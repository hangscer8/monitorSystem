package json
object Protocol {
  sealed trait Base
  case class Agent(host: String, port: Int) extends Base

}
