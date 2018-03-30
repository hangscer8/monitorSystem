package nathan.util

object CommonUtil {

  implicit class SlashString(prefixStr: String) {
    def /(string: String) = prefixStr + "/" + string
  }

  implicit class AnyOps[T](t: T) {
    def as[O]: O = t.asInstanceOf[O]
  }

}