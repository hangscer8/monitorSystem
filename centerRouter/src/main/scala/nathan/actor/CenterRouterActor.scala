package nathan.actor

import akka.actor.{Actor, Props}
import nathan.monitorSystem.AkkaSystemConst._
import nathan.util.ImperativeRequestContext

class CenterRouterActor extends Actor {
  override def receive: Receive = {
    case (ctx: ImperativeRequestContext, ip: String, port: Int) =>
      context.actorOf(Props(classOf[PeerToAgentActor])) ! (ctx, ip, port) //转发
    case (`agentTimeout`, agentId: String) =>
      println(agentId + "退出了")
  }
}