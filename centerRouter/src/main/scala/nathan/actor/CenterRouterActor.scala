package nathan.actor

import akka.actor.{Actor, Props}
import nathan.monitorSystem.akkaAction.{AgentActorJoinCenter, AkkaEventAction}
import nathan.monitorSystem.akkaSystemConst._

class CenterRouterActor extends Actor {
  override def receive: Receive = {
    case AgentActorJoinCenter(agentActor, agentMachineEntity) =>
      context.actorOf(Props(classOf[PeerToAgentActor])) ! (`agentActorJoined`, agentActor, agentMachineEntity)
    case (`agentTimeout`, agentId: String) =>
      println(agentId + "退出了")
  }
}