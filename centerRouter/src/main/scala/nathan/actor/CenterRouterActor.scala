package nathan.actor

import akka.actor.{Actor, ActorLogging, Props}
import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._
import nathan.monitorSystem.AkkaSystemConst._
import nathan.util.FutureUtil.FutureOps
import nathan.util.ImperativeRequestContext

class CenterRouterActor extends Actor with ActorLogging{
  override def receive: Receive = {
    case (ctx: ImperativeRequestContext, ip: String, port: Int) =>
      context.actorOf(Props(classOf[PeerToAgentActor])) ! (ctx, ip, port) //转发
    case (`agentTimeout`, agentId: String) =>
      db.run(agentMachines.filter(_.agentId === agentId).delete.asTry).exec
      log.warning(s"agentId为${agentId}离线了")
  }
}