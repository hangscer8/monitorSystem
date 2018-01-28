package nathan.actor

import akka.actor.{Actor, ActorRef, ReceiveTimeout}
import nathan.monitorSystem.Protocols.{AgentMachineEntity, BaseAgentInfo}
import nathan.monitorSystem.akkaSystemConst._

import scala.concurrent.duration._

class PeerToAgentActor extends Actor {
  override def receive: Receive = {
    case (`agentActorJoined`, agent: ActorRef, agentMachine: AgentMachineEntity) =>
      agent ! (peerToAgentActor, self)
      context.setReceiveTimeout(2.2 seconds)
      context.become(running(agent, agentMachine.agentId))
  }

  def running(agent: ActorRef, agentId: String): Receive = {
    case any: BaseAgentInfo =>
      println(any)
    case ReceiveTimeout =>
      context.parent ! (agentTimeout, agentId)
      context.stop(self)
  }
}