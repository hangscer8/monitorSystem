package service

object Protocol {
  val host = "127.0.0.1"
  val port = 8888
  val baseUrl = s"http://$host:$port"
  val prefix = "monitorSystem"
  val authHead = "MONITORSYSTEM-AUTH"
  object DefaultAuth{
    val authValue="qazwsxedc"
  }
  implicit class SlashString(prefixStr: String) {
    def /(string: String) = prefixStr + "/" + string
  }

}
