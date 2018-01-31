package nathan.util

import nathan.monitorSystem.AkkaSystemConst
import org.scalajs.dom._

trait HttpHeadSupport extends AkkaSystemConst{
  val `Content-Type` = "Content-Type"
  val `"application/json` = "application/json"

  def header = Map("Content-Type" -> "application/json", authHead -> window.localStorage.getItem(authHead))
}