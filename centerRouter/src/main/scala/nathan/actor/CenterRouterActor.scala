package nathan.actor

import akka.actor.{Actor, ActorRef}

class CenterRouterActor extends Actor with RouterActorTrait {
  var agentOnLine = List.empty[ActorRef] //在线的agent
  override def receive: Receive = {
    case str: String =>
      withMsg(str)
  }
}

trait RouterActorTrait {
  i: Actor =>
  def withMsg(str: String) = {
    
  }
}