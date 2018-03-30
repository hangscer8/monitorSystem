package controllers.register

import java.io.File
import javax.inject._

import conf.PlayConf
import io.circe.Printer
import io.circe.generic.auto._
import io.circe.syntax._
import nathan.monitorSystem.Protocols.RegisterReq
import play.api.libs.circe.Circe
import play.api.mvc._
import service.register.RegisterServiceTrait
import util.ActionHelper

@Singleton
class RegisterController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with ActionHelper with RegisterServiceTrait {
  implicit val codec = Codec.utf_8
  implicit val customPrinter = Printer.spaces2.copy(dropNullValues = false)

  def index = LoginAction { request =>
    Ok(views.html.register.index("注册页面"))
  }

  def registerUser() = LoggingAction(circe.json[RegisterReq]) { request => //json反序列化
    Ok("hahahsa" + request.body)
  }

  def upload() = LoggingAction(parse.multipartFormData) { request =>
    request.body.file("file").map(file => file.ref.moveTo(new File(PlayConf.uploadDir, file.filename), true))
    Ok(Map("code" -> "haha", "message" -> "上传成功!").asJson.noSpaces).as(JSON) //json序列化
  }
}