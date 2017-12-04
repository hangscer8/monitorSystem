package nathan.protocol

case class RetMsg(code: String, entity: String)

object CustomerCodes {
  val success = "0000"
  val error = "0001"
}