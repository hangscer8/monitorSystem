package util
import org.scalajs.dom._
import util.CommonConst._
trait HttpHeadSupport {
  val `Content-Type` = "Content-Type"
  val `"application/json` = "application/json"
  def header = Map("Content-Type" -> "application/json", authHead -> window.localStorage.getItem(authHead))
}