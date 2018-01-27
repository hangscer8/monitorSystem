package nathan.actor

import akka.actor.{Actor, ActorRef}
import nathan.monitorSystem.Protocols.BaseAgentInfo
import nathan.monitorSystem.akkaSystemConst._

import scala.concurrent.duration._

class PeerToAgentActor extends Actor {
  override def receive: Receive = {
    case (`agentActorJoined`, agent: ActorRef) =>
      agent ! (peerToAgentActor, self)
      context.setReceiveTimeout(3 seconds)
      context.become(running(agent))
  }

  def running(agent: ActorRef): Receive = {
    case any: BaseAgentInfo =>
      println(any)
  }
}
