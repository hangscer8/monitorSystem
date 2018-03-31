package controllers.stream

import java.io.File

import conf.PlayConf
import javax.inject._
import play.api.libs.circe.Circe
import play.api.mvc._
import util.ExecutorService._

class StreamFileController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe {
  def streamFile(file: String) = Action { request =>
    Ok.sendFile(new File(PlayConf.uploadDir + File.separator + file))
  }
}