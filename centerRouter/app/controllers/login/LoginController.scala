package controllers.login

import java.io.File
import javax.inject._

import conf.PlayConf
import io.circe.Printer
import io.circe.generic.auto._
import io.circe.syntax._
import nathan.monitorSystem.Protocols.UserEntity
import play.api.libs.circe.Circe
import play.api.mvc._
import service.register.RegisterServiceTrait
import util.{ActionHelper, UtilTrait}
import entity.EntityTable._
import h2.api._
import service.login.LoginServiceTrait
import util.ExecutorService._

import scala.util.{Failure, Success}

@Singleton
class LoginController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with LoginServiceTrait with Circe with ActionHelper with UtilTrait {
  def index = LoginAction { request =>
    Ok(views.html.login.index("登陆页面"))
  }

  def loginAction = LoggingAction(circe.json[Map[String, String]]) { request =>
    db.run(userExistDBIO(request.body("username"), request.body("password"))).map {
      case true => Ok(Map("code" -> "0000").asJson.noSpaces).as(JSON).withSession((loginKey, "true"))
      case false => Ok(Map("code" -> "0001").asJson.noSpaces).as(JSON).withNewSession
    }.exe
  }
}