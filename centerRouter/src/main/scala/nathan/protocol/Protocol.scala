package nathan.protocol

object Protocol {
  trait Base
  case class Agent(host: String, port: Int)

  case class UserReq(username: String, password: String, type1: String)

}