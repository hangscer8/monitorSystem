package nathan.service.user

object Protocol {

  case class UserReq(username: String, password: String, `type`: String)

}
