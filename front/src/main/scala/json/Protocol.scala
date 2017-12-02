package json
object Protocol {
  sealed trait Base
  case class Agent(host: String, port: Int) extends Base
  case class UserEntity(id: Long, username: String, password: String, `type`: String, lastActiveTime: Long, auth: Option[String])
  
}
object UserTypeConst {
  val common = "common"
  val admin = "admin"
}