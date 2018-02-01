package nathan.actor

import akka.actor.{Actor, Props}
import nathan.actor.Protocol.StoreAgentMachineAction
import nathan.ec.ExecutorService.daoActor
import nathan.monitorSystem.AkkaSystemConst._
import nathan.monitorSystem.akkaAction.AgentActorJoinCenter

class CenterRouterActor extends Actor {
  override def receive: Receive = {
    case AgentActorJoinCenter(agentActor, agentMachineEntity) =>
      daoActor ! StoreAgentMachineAction(agentMachineEntity)
      context.actorOf(Props(classOf[PeerToAgentActor])) ! (`agentActorJoined`, agentActor, agentMachineEntity)
    case (`agentTimeout`, agentId: String) =>
      println(agentId + "退出了")
  }
}