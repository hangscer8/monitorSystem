package nathan.protocol

trait RetMsg

case class RetSuccess(code: String, entity: Any) extends RetMsg

case class RetFail(code: String, msg: String) extends RetMsg

object CustomerCodes {
  val success = "0000"
  val error = "0001"
}
