package nathan.actor

import akka.actor.{Actor, ActorRef}
import nathan.monitorSystem.akkaSystemConst._

class PeerToAgentActor extends Actor {
  override def receive: Receive = {
    case (`agentActorJoined`, agent: ActorRef) =>
      agent ! (peerToAgentActor, self)
  }
}
