package nathan.actor

import akka.actor.{Actor, Props}
import nathan.monitorSystem.AkkaSystemConst._

class AgentActor extends Actor {

  override def receive: Receive = {
    case `askAgent` =>
      val peertoAgentActor = sender()
      val agentId = scala.util.Random.alphanumeric.take(40).mkString
      val collectionMetricActor = context.actorOf(Props(classOf[CollectionMetricActor]))
      collectionMetricActor ! ("peertoAgentActor", agentId, peertoAgentActor)
  }
}