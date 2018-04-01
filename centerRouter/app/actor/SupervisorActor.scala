package actor

import akka.actor.{Actor, ActorLogging, Props, Terminated}
import nathan.monitorSystem.Protocols.AddAgentReq
import play.api.http.ContentTypes
import play.api.mvc.{AnyContent, Results}
import util.ActionContext
import io.circe.syntax._

class SupervisorActor extends Actor with ActorLogging { //集群监控系统中的中心路由
  override def receive: Receive = {
    case (ctx: ActionContext[AnyContent], addAgent: AddAgentReq) =>
      val p2pActorName = s"p2pActor_${addAgent.ip}_${addAgent.port}"
      context.child(p2pActorName) match {
        case Some(_) =>
          ctx.complete(Results.Ok(Map("code" -> "0001", "errorMsg" -> s"目标地址agent服务(${addAgent.ip}:${addAgent.port}已经添加过!!，不需要重复添加.)").asJson.noSpaces).as(ContentTypes.JSON))
        case None =>
          val p2pActor = context.actorOf(Props[PeerToPeerActor], s"p2pActor_${addAgent.ip}_${addAgent.port}") //与远程的agent建立一对一的连接
          context.watch(p2pActor)
          p2pActor ! (ctx, addAgent)
      }
    case Terminated(actor) =>
      log.info(s"${actor}退出了")
  }
}