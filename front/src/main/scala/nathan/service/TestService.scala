package nathan.service

import io.circe.Decoder
import io.circe.generic.semiauto._
import io.circe.parser.decode
import nathan.monitorSystem.AkkaSystemConst
import nathan.monitorSystem.Protocols._
import nathan.util.CommonUtil._
import nathan.util.HttpHeadSupport
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.annotation.JSExport
import scala.util.{Failure, Success}

@JSExport
object TestService extends HttpHeadSupport with AkkaSystemConst{
  @JSExport
  def test(): Unit = {
    UserService.setAuth()
    val agentId = "myAgent123"
    Ajax.get(url = baseUrl / prefix / "cpuPerc?" + "agentId=" + agentId, headers = header - (`Content-Type`)).onComplete {
      case Success(xhr) =>
        val result = decode[Seq[CPUPercEntity]](xhr.responseText)
        println(result)
      case Failure(ex) => console.log(ex.getMessage)
    }
  }

  implicit val cpuPercEntityDecoder: Decoder[CPUPercEntity] = deriveDecoder[CPUPercEntity]
}