package nathan.actor

import akka.actor.{Actor, Props}
import nathan.monitorSystem.Protocols.BaseAgentInfo
import nathan.monitorSystem.akkaAction.{AgentActorJoinCenter, AkkaEventAction}
import nathan.monitorSystem.akkaSystemConst._

class CenterRouterActor extends Actor with RouterActorTrait {
  override def receive: Receive = {
    case any: BaseAgentInfo =>
      withBaseAgentInfo(any)
    case any: AkkaEventAction =>
      withAkkaEventAction(any)
  }
}

trait RouterActorTrait {
  i: Actor =>
  def withBaseAgentInfo(agentInfo: BaseAgentInfo) = {
    print(agentInfo)
  }

  def withAkkaEventAction(akkaEventAction: AkkaEventAction) = {
    akkaEventAction match {
      case AgentActorJoinCenter(agentActor) =>
        println(agentActor)
        context.actorOf(Props(classOf[PeerToAgentActor])) ! (`agentActorJoined`, agentActor)
    }
  }
}