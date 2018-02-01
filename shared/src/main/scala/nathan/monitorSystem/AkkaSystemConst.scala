package nathan.monitorSystem

object AkkaSystemConst {
  val center_port = 2551
  val agent_port = 2552
  val center_system_name = "centerRouterActor"
  val center_actor_name = "centerSystem"
  val dao_actor_name = "daoActor"
  val agent_system_name = "agentSystem"
  val agent_actor_name = "agentActor"
  val agentActorJoined = "agentActor"
  val peerToAgentActor = "peerToAgentActor"
  val agentTimeout = "agentTimeout"

  val akkaServerPort=8888
  val akkaServerIp="127.0.0.1"
  val baseUrl = s"http://$akkaServerIp:$akkaServerPort"
  val prefix = "monitorSystem"
  val authHead = "MONITORSYSTEM-AUTH"
}