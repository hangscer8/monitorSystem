package nathan.actor

import akka.actor.Actor

class AgentActor extends Actor with AgentActorBaseTrait {
  override def receive: Receive = {
    case str:String=>
      withMsg(str)
  }
}
trait AgentActorBaseTrait{ i:Actor=>
  def withMsg(str: String)={
    
  }
}