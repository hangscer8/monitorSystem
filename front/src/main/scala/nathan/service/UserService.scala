package nathan.service

import com.highcharts.HighchartsAliases._
import com.highcharts.HighchartsUtils._
import com.highcharts.config._
import io.circe.Decoder
import io.circe.generic.auto._
import io.circe.generic.semiauto._
import io.circe.parser.decode
import io.circe.syntax._
import nathan.entity.{LoginReq, RetMsg}
import nathan.util.CommonConst._
import nathan.util.CommonUtil._
import nathan.util.HttpHeadSupport
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData
import org.scalajs.dom.html.Input
import org.scalajs.jquery.jQuery

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scala.util.{Failure, Success}

@JSExport
object UserService extends HttpHeadSupport {
  @JSExport
  def setAuth(authOption: Option[String] = None) = authOption match {
    case _: Some[_] =>
      window.localStorage.setItem(authHead, authOption.get)
    case None =>
      window.localStorage.setItem(authHead, scala.util.Random.alphanumeric.take(20).mkString)
  }

  def getAuth = window.localStorage.getItem(authHead)

  @JSExport
  def loginForm(form: html.Form) = {
    val _inputs = form.getElementsByTagName("input")
    val inputs = (0 until _inputs.length).map(_inputs(_).asInstanceOf[Input])
    val username = inputs.filter(_.name == "username").head.value
    val password = inputs.filter(_.name == "password").head.value
    val `type` = inputs.filter(_.name == "type").filter(_.checked == true).head.value
    login(LoginReq(username, password, `type`)).onComplete {
      case Success(xhr) =>
        console.log(xhr.responseType)
        //        console.log(xhr.response)
        decode[RetMsg](xhr.responseText.toString) match {
          case Right(msg) =>
            console.log(msg.code)
          case Left(ex) =>
            console.error("asd")
            console.log(ex.getMessage)
        }
      case Failure(ex) =>
        console.error(ex + "")
    }
  }

  def login(user: LoginReq) = {
    Ajax.post(url = baseUrl / "monitorSystem" / "login", data = InputData.str2ajax(user.asJson.noSpaces), headers = header)
  }

  implicit val retSuccessDecoder: Decoder[RetMsg] = deriveDecoder[RetMsg]

  @JSExport
  def testHc() = {
    val newChart = (datas: js.Array[AnySeries]) =>
      new HighchartsConfig {
        // Chart config
        override val chart: Cfg[Chart] = Chart(`type` = "line", animation = true)
        //      override val chart: Cfg[Chart] = Chart()

        // Chart title
        override val title: Cfg[Title] = Title(text = "Demo bar chart")

        // X Axis settings
        override val xAxis = js.Array(XAxis(categories = js.Array("Apples", "Bananas", "Oranges")))

        // Y Axis settings
        override val yAxis = js.Array(YAxis(min = -.0, max = 20.0, title = YAxisTitle(text = "Fruit eaten")))
        // Series
        override val series: SeriesCfg = datas
        override val credits = Credits(false)

        override val legend = Legend(layout = "horizontal")
      }
    window.setInterval(() => {
      val now = System.currentTimeMillis()
      val data = js.Array[AnySeries](
        SeriesLine(name = "蒋航122", data = js.Array[Double](2, 3, 8)),
        SeriesLine(name = "是这样1221", data = js.Array[Double](10, 15, 7))
      )
      val data1 = js.Array[AnySeries](
        SeriesLine(name = "Jane112", data = js.Array[Double](1, 0, 4)),
        SeriesLine(name = "John", data = js.Array[Double](5, 7, 3))
      )
      now % 2 == 0 match {
        case true =>
          jQuery("#container").highcharts(newChart(data))
        case false =>
          jQuery("#container").highcharts(newChart(data1))
      }

    }, 3000.0)
  }
}
class Animation extends js.Object {

}