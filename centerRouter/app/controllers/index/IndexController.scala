package controllers.index

import javax.inject._
import io.circe.Printer
import play.api.libs.circe.Circe
import play.api.mvc._
import util.ActionHelper

@Singleton
class IndexController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with ActionHelper {
  def index() = LoginAction { request =>
    Ok(views.html.index.index("首页"))
  }
}