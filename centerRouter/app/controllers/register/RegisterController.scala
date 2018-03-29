package controllers.register

import javax.inject._
import io.circe.Printer
import nathan.monitorSystem.Protocols.RegisterReq
import play.api.libs.circe.Circe
import play.api.mvc._
import util.ActionHelper
import io.circe.generic.auto._

@Singleton
class RegisterController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with ActionHelper {
  implicit val codec = Codec.utf_8
  implicit val customPrinter = Printer.spaces2.copy(dropNullValues = false)

  def index = LoggingAction { request =>
    Ok(views.html.register.index("注册页面"))
  }

  def registerUser() = LoggingAction(circe.json[RegisterReq]) { request =>
    Ok("hahahsa" + request.body)
  }

  def upload() = LoggingAction(parse.multipartFormData) { request =>
    Ok("asdasd")
  }
}