package actor

import akka.actor.{Actor, ActorLogging, Props, Terminated}
import nathan.monitorSystem.Protocols.AddAgentReq
import play.api.mvc.AnyContent
import util.ActionContext

class SupervisorActor extends Actor with ActorLogging { //集群监控系统中的中心路由
  override def receive: Receive = {
    case (ctx: ActionContext[AnyContent], addAgent: AddAgentReq) =>
      val p2pActor = context.actorOf(Props[PeerToPeerActor], s"p2pActor_${addAgent.ip}_${addAgent.port}") //与远程的agent建立一对一的连接
      context.watch(p2pActor)
      p2pActor ! (ctx, addAgent)
    case Terminated(actor) =>
      log.info(s"${actor}退出了")
  }
}