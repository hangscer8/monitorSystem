package util

object CommonUtil {
  implicit class SlashString(prefixStr: String) {
    def /(string: String) = prefixStr + "/" + string
  }
}
