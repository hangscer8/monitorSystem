package nathan.actor

import akka.actor.{Actor, ActorRef}
import nathan.OS
import nathan.monitorSystem.akkaAction.AgentActorJoinCenter
import nathan.monitorSystem.akkaSystemConst._

import scala.concurrent.duration._

class AgentActor extends Actor {
  import context.dispatcher
  override def preStart(): Unit = {
    val path = s"akka.tcp://$center_system_name@127.0.0.1:$center_port/user/$center_actor_name"
    context.actorSelection(path) ! AgentActorJoinCenter(self)
    context.system.scheduler.schedule(1 seconds, 2 seconds, self, "sendInfoToPeerActor")
  }

  override def receive: Receive = {
    case (`peerToAgentActor`, peerActor: ActorRef) =>
      context.become(running(peerActor))
  }

  def running(peerActor: ActorRef): Receive = {
    case "sendInfoToPeerActor" =>
      peerActor ! OS.getCPUPerc()
  }
}