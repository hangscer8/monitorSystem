package nathan.util

import java.util.Date

trait CommonUtilTrait {
  val `Content-Type` = "Content-Type"

  val `application/json` = "application/json"

  implicit class SlashString(prefixStr: String) {
    def /(string: String) = prefixStr + "/" + string
  }

  implicit class AnyOps[T](t: T) {
    def as[O]: O = t.asInstanceOf[O]
  }

  def genSeries(series: List[Serie]): String = {
    def timeStamp2Date(timeStamp: Long, value: Double) = {
      val target = new Date(timeStamp)
      val year = 1900 + target.getYear
      val month = target.getMonth
      val date = target.getDate
      val hour = target.getHours
      val min = target.getMinutes
      val sec = target.getSeconds
      s"[Date.UTC($year,$month,$date,$hour,$min,$sec),$value]"
    }

    val strList = series.map { s =>
      s"""
         |{
         |  type: '${s.`type`}',
         |  name: '${s.name}',
         |  data: [${s.list.map(t => timeStamp2Date(t._1, t._2)).mkString(",")}]
         |}
       """.stripMargin
    }
    s"[${strList.mkString(",")}]"
  }

  case class Serie(`type`: String, name: String, list: Seq[(TimeStamp, Double)])

  type TimeStamp = Long
}