package nathan.protocol

object Protocol {
  trait Base
  final case class Agent(host: String, port: Int)

  final case class UserReq(username: String, password: String, `type`: String)
}
