package controllers

import javax.inject._

import io.circe.Printer
import play.api.libs.circe.Circe
import play.api.mvc._

@Singleton
class RegisterController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe {
  implicit val codec = Codec.utf_8
  implicit val customPrinter = Printer.spaces2.copy(dropNullValues = false)
  
  def index() = Action {
    Ok(views.html.register.main("sadasd蒋航"))
  }
}