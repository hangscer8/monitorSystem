package nathan.util

trait CommonUtilTrait {
  val `Content-Type` = "Content-Type"

  val `application/json` = "application/json"
  
  implicit class SlashString(prefixStr: String) {
    def /(string: String) = prefixStr + "/" + string
  }

  implicit class AnyOps[T](t: T) {
    def as[O]: O = t.asInstanceOf[O]
  }

}