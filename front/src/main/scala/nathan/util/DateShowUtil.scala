package nathan.util

import scala.scalajs.js.Date

object DateShowUtil {
  def show(time: Long): String = {
    val date = new Date(time)
    s"${date.getFullYear()}.${date.getMonth() + 1}.${date.getDate()}.${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}"
  }
}