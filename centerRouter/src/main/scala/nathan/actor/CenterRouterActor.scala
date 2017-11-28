package nathan.actor

import java.util.concurrent.ConcurrentHashMap

import akka.actor.{Actor, ActorRef}

class CenterRouterActor extends Actor {
  override def receive: Receive = {
    case str:String =>
      
  }
}
object AgentActorStorge{
  val agentActorMap=new ConcurrentHashMap[String,ActorRef]()
}