package nathan.util

import nathan.monitorSystem.AkkaSystemConst._
import org.scalajs.dom.window

object CommonUtil {

  implicit class SlashString(prefixStr: String) {
    def /(string: String) = prefixStr + "/" + string
  }

  def setAuth: String = {
    val key = scala.util.Random.alphanumeric.take(40).mkString
    window.localStorage.setItem(authHead, key)
    key
  }

  def getAuth: String = {
    window.localStorage.getItem(authHead)
  }
}