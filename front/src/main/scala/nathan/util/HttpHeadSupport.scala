package nathan.util

import nathan.monitorSystem.AkkaSystemConst._
import org.scalajs.dom._
import window.localStorage

trait HttpHeadSupport {
  val `Content-Type` = "Content-Type"
  val `application/json` = "application/json"

  def header = Map("Content-Type" -> "application/json", authHead -> localStorage.getItem(authHead))

  def headerWithJson = Map("Content-Type" -> "application/json", authHead -> localStorage.getItem(authHead))
}