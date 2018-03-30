package controllers.stream

import java.io.File

import conf.PlayConf
import javax.inject._
import play.api.libs.circe.Circe
import play.api.mvc._
import util.ActionHelper
import util.ExecutorService._

class StreamFileController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with ActionHelper {
  def streamFile(file: String) = LoggingAction { request =>
    Ok.sendFile(new File(PlayConf.uploadDir + File.separator + file))
  }
}