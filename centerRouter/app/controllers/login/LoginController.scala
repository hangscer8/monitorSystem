package controllers.login

import javax.inject._

import entity.EntityTable._
import io.circe.syntax._
import play.api.libs.circe.Circe
import play.api.mvc._
import service.login.LoginServiceTrait
import util.ExecutorService._
import util.UtilTrait

@Singleton
class LoginController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with LoginServiceTrait with Circe with UtilTrait {
  def index = Action { request =>
    Ok(views.html.login.index("登陆页面"))
  }

  def loginAction = Action(circe.json[Map[String, String]]).async { request =>
    db.run(userExistDBIO(request.body("username"), request.body("password"))).map {
      case true => Ok(Map("code" -> "0000").asJson.noSpaces).as(JSON).withSession((loginKey, "true"))
      case false => Ok(Map("code" -> "0001").asJson.noSpaces).as(JSON).withNewSession
    }
  }
}