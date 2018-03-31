package controllers

import javax.inject._
import io.circe.Printer
import play.api.libs.circe.Circe
import play.api.mvc._
import util.ActionHelper

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with ActionHelper {
  implicit val codec = Codec.utf_8
  implicit val customPrinter = Printer.spaces2.copy(dropNullValues = false)

  def index() = LoggingAction { request =>
    Ok(views.html.main("asda"))
  }
}