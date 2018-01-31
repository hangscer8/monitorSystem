package nathan.entity

sealed trait Base

case class Agent(host: String, port: Int) extends Base

case class UserReq(username: String, password: String, `type`: String)

case class LoginReq(username: String, password: String, `type`: String)

case class RetMsg(code: String, entity: String)