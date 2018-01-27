package nathan.actor

import akka.actor.{Actor, Props}
import nathan.monitorSystem.akkaAction.{AgentActorJoinCenter, AkkaEventAction}
import nathan.monitorSystem.akkaSystemConst._

class CenterRouterActor extends Actor with RouterActorTrait {
  override def receive: Receive = {
    case any: AkkaEventAction =>
      withAkkaEventAction(any)
  }
}

trait RouterActorTrait {
  i: Actor =>
  def withAkkaEventAction(akkaEventAction: AkkaEventAction) = {
    akkaEventAction match {
      case AgentActorJoinCenter(agentActor) =>
        println(agentActor)
        context.actorOf(Props(classOf[PeerToAgentActor])) ! (`agentActorJoined`, agentActor)
    }
  }
}