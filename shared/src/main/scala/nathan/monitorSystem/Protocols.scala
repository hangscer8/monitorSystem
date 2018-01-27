package nathan.monitorSystem

import akka.actor.ActorRef


package Protocols {

  trait BaseAgentInfo

  case class CPUPerc(user: Double, sys: Double, _wait: Double, idle: Double, combined: Double) extends BaseAgentInfo

  //user用户使用率 sys系统使用率 _wait当前等待率 idle空闲率 combined 总的使用率
  case class ME()

}

object akkaSystemConst {
  val center_port = 2551
  val agent_port = 2552
  val center_system_name = "centerRouterActor"
  val center_actor_name = "centerSystem"
  val agent_system_name = "agentSystem"
  val agent_actor_name = "agentActor"
  val agentActorJoined = "agentActor"
  val peerToAgentActor = "peerToAgentActor"
}

object akkaAction {

  trait AkkaEventAction

  case class AgentActorJoinCenter(agentActor: ActorRef) extends AkkaEventAction

}