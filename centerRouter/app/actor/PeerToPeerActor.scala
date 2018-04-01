package actor

import akka.actor.{Actor, ActorLogging, ActorPaths, ActorRef, ReceiveTimeout, Terminated}
import entity.EntityTable._
import entity.EntityTable.h2.api._
import nathan.monitorSystem.AkkaSystemConst.{agent_actor_name, agent_system_name, _}
import nathan.monitorSystem.Protocols.{AddAgentReq, AgentMachineEntity, BaseAgentInfo}
import nathan.monitorSystem.akkaAction.AgentActorJoinCenter
import play.api.http.ContentTypes
import play.api.mvc.{AnyContent, Codec, Results}
import service.agent.AgentServiceTrait
import util.{ActionContext, UtilTrait}
import io.circe.syntax._
import service.metric.MetricServiceTrait

import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

class PeerToPeerActor extends Actor with ActorLogging with AgentServiceTrait with UtilTrait with MetricServiceTrait {
  implicit val codec = Codec.utf_8

  override def receive: Receive = {
    case (ctx: ActionContext[AnyContent], addAgent: AddAgentReq) =>
      val agentPathStr = s"akka.tcp://$agent_system_name@${addAgent.ip}:${addAgent.port}/user/$agent_actor_name"
      Try(ActorPaths.fromString(agentPathStr)) match {
        case Success(path) =>
          context.actorSelection(path) ! askAgent //向目标agent发起ask请求
          context.setReceiveTimeout(1.8 seconds) //1.8秒内需要收到目标agent的回复，不然向前台返回超时提示
          context.become(waitForAgent(ctx, addAgent))
        case Failure(ex) =>
          ctx.complete(Results.Ok(Map("code" -> "0001", "errorMsg" -> s"${ex}").asJson.noSpaces).as(ContentTypes.JSON))
          context.stop(self)
      }
  }

  def waitForAgent(ctx: ActionContext[AnyContent], addAgentReq: AddAgentReq): Receive = {
    case ReceiveTimeout =>
      ctx.complete(Results.Ok(Map("code" -> "0001", "errorMsg" -> s"添加agent超时，有可能目标地址(${addAgentReq.ip}:${addAgentReq.port})不存在agent服务!!").asJson.noSpaces).as(ContentTypes.JSON))
      context.stop(self)
    case AgentActorJoinCenter(agentActor, agentMachineEntity) =>
      db.run(createAgentDBIO(agentMachineEntity).transactionally.asTry).exe match {
        case Success(_) =>
          context.watch(agentActor)
          log.info(s"已经与${addAgentReq.ip}:${addAgentReq.port}建立连接!!")
          context.become(connected(agentActor, agentMachineEntity))
          ctx.complete(Results.Ok(Map("code" -> "0000", "successMsg" -> s"添加agent成功，目标地址(${addAgentReq.ip}:${addAgentReq.port})存在agent服务!!").asJson.noSpaces).as(ContentTypes.JSON))
        case Failure(ex) =>
          ctx.complete(Results.Ok(Map("code" -> "0001", "errorMsg" -> s"数据库添加记录失败,$ex").asJson.noSpaces).as(ContentTypes.JSON))
          context.stop(self)
      }
  }

  def connected(agentActor: ActorRef, agentMachineEntity: AgentMachineEntity): Receive = {
    case Terminated(actor) =>
      if (actor == agentActor) {
        log.error(s"${agentMachineEntity.ip}:${agentMachineEntity.akkaPort}已经退出连接!")
        db.run(deleteAgentDBIO(agentMachineEntity.id).transactionally.asTry).exe
        context.stop(self)
      }
    case x: BaseAgentInfo =>
      createMetric(x) match {
        case Success(v) =>
          db.run(increaseReceiveMsgNumberDBIO(v.agentId).transactionally.asTry).exe
          log.info(s"${Console.BLUE}success insert metric:${Console.RESET}:$v")
        case Failure(ex) => log.error(s"${Console.RED}failure insert metric:${Console.RESET}:$ex")
      }
  }
}