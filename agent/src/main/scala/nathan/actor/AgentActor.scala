package nathan.actor

import akka.actor.Actor
import nathan.OS
import nathan.monitorSystem.akkaSystemConst._

import scala.concurrent.duration._

class AgentActor extends Actor with AgentActorBaseTrait {

  import context.dispatcher

  context.system.scheduler.schedule(2 seconds, 2 second) {
    val path = s"akka.tcp://$center_system_name@127.0.0.1:$center_port/user/$center_actor_name"
    context.actorSelection(path) ! OS.getCPUPerc()
  }

  override def receive: Receive = {
    case str: String =>

  }
}

trait AgentActorBaseTrait {
  i: Actor =>
  def withMsg(str: String) = {

  }
}