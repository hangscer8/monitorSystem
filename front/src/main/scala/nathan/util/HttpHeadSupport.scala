package nathan.util

import nathan.monitorSystem.AkkaSystemConst._
import org.scalajs.dom._

trait HttpHeadSupport {
  val `Content-Type` = "Content-Type"
  val `"application/json` = "application/json"

  def header = Map("Content-Type" -> "application/json", authHead -> window.localStorage.getItem(authHead))
}