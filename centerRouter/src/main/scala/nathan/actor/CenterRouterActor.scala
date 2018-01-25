package nathan.actor

import akka.actor.{Actor, ActorRef}
import nathan.monitorSystem.Protocols.BaseAgentInfo

class CenterRouterActor extends Actor with RouterActorTrait {
  println(self.path)
  var agentOnLine = List.empty[ActorRef] //在线的agent
  override def receive: Receive = {
    case any: BaseAgentInfo =>
      withMsg(any)
  }
}

trait RouterActorTrait {
  i: Actor =>
  def withMsg(agentInfo: BaseAgentInfo) = {
    print(agentInfo)
  }
}