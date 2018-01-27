package nathan.actor

import akka.actor.{Actor, ActorRef, Terminated}
import nathan.monitorSystem.Protocols.BaseAgentInfo
import nathan.monitorSystem.akkaSystemConst._

class PeerToAgentActor extends Actor {
  override def receive: Receive = {
    case (`agentActorJoined`, agent: ActorRef, agentId: String) =>
      agent ! (peerToAgentActor, self)
      context.watch(agent)
      context.become(running(agent, agentId))
  }

  def running(agent: ActorRef, agentId: String): Receive = {
    case any: BaseAgentInfo =>
      println(any)
    case Terminated(`agent`) =>
      println(agent + "挂了")
  }
}