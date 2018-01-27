package nathan.actor

import akka.actor.{Actor, ActorRef}
import nathan.monitorSystem.akkaAction.AgentActorJoinCenter
import nathan.monitorSystem.akkaSystemConst._

class AgentActor extends Actor {

  override def preStart(): Unit = {
    val path = s"akka.tcp://$center_system_name@127.0.0.1:$center_port/user/$center_actor_name"
    context.actorSelection(path) ! AgentActorJoinCenter(self)
  }

  override def receive: Receive = {
    case (`peerToAgentActor`, peerActor: ActorRef) =>
      
  }
}