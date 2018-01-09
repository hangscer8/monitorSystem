package nathan.json

object Protocol {

  trait Entity

  case class InfoMsg(infoType: String, entity: String)

}