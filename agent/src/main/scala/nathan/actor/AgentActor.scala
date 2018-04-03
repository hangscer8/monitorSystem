package nathan.actor

import akka.actor.{Actor, Props}
import nathan.monitorSystem.AkkaSystemConst._
import nathan.util.AgentId

class AgentActor extends Actor {

  override def receive: Receive = {
    case `askAgent` =>
      val peertoAgentActor = sender()
      val agentId = AgentId.agentIdString
      val collectionMetricActor = context.actorOf(Props(classOf[CollectionMetricActor]))
      collectionMetricActor ! ("peertoAgentActor", agentId, peertoAgentActor)
  }
}