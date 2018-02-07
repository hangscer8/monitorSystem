package nathan.actor

import akka.actor.{Actor, ActorPaths, ActorRef, ReceiveTimeout}
import nathan.ec.ExecutorService.daoActor
import nathan.monitorSystem.Protocols._
import nathan.actor.Protocol._
import nathan.monitorSystem.AkkaSystemConst._
import nathan.monitorSystem.MsgCode
import nathan.monitorSystem.akkaAction.AgentActorJoinCenter
import nathan.util.ImperativeRequestContext
import nathan.util.JsonUtil._

import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

class PeerToAgentActor extends Actor {
  override def receive: Receive = {
    case (ctx: ImperativeRequestContext, ip: String, port: Int) =>
      val agentActorPathStr = s"akka.tcp://$agent_system_name@${ip}:${port}/user/$agent_actor_name"
      Try(ActorPaths.fromString(agentActorPathStr)) match {
        case Success(path) =>
          Try(context.actorSelection(path) ! askAgent)
          context.setReceiveTimeout(2 seconds)
          context.become(waitingAgentJoin(ctx))
        case Failure(exception) =>
          ctx.complete(exception.getMessage)
          context.stop(self)
      }
  }

  def waitingAgentJoin(ctx: ImperativeRequestContext): Receive = {
    case ReceiveTimeout =>
      ctx.complete("agent joined time out!")
      context.stop(self)
    case AgentActorJoinCenter(agentActor, agentMachineEntity) =>
      ctx.complete(MsgCode.success)
      context.become(running(agentActor, agentMachineEntity.agentId))
  }

  def running(agent: ActorRef, agentId: String): Receive = {
    case any: BaseAgentInfo => any match {
      case cPUPercEntity: CPUPercEntity =>
        daoActor ! StoreCPUPercAction(cPUPercEntity)
      case memEntity: MEMEntity =>
        daoActor ! StoreMEMEntityAction(memEntity)
      case swapEntity: SWAPEntity =>
        daoActor ! StoreSWAPEntityAction(swapEntity)
      case loadAvgEntity: LoadAvgEntity =>
        daoActor ! StoreLoadAvgEntityAction(loadAvgEntity)
      case fileUsageEntity: FileUsageEntity =>
        daoActor ! StoreFileUsageEntityAction(fileUsageEntity)
      case netInfoEntity: NetInfoEntity =>
        daoActor ! StoreNetInfoEntityAction(netInfoEntity)
    }
    case ReceiveTimeout =>
      context.parent ! (agentTimeout, agentId)
      context.stop(self)
  }
}