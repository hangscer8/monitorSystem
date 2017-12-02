package nathan.router

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

object Protocol {
  val prefix = "monitorSystem"
  val auth = "MONITORSYSTEM-AUTH"

  case class Agent(host: String, port: Int)

}

trait JsonSupport extends DefaultJsonProtocol with SprayJsonSupport {

  import nathan.router.Protocol._

  implicit val agentFormt = jsonFormat2(Agent)
}