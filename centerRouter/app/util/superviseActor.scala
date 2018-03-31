package util

import akka.actor.{Actor, ActorRef, Props}
import play.api.mvc.Action

class ManagerActor extends Actor { //akka创建actor
  override def receive: Receive = {
    case (props: Props, actorName: String) => sender() ! context.actorOf(props, actorName)
    case pros: Props => sender() ! context.actorOf(pros)
  }
}