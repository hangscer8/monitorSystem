package controllers.agent

import javax.inject.Inject
import io.circe.Printer
import io.circe.generic.auto._
import io.circe.syntax._
import nathan.monitorSystem.Protocols.AddAgentReq
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import service.agent.AgentServiceTrait
import util.ActionHelper

class AgentController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with ActionHelper with AgentServiceTrait {
  def addAgent() = LoginAction(circe.json[AddAgentReq]) { request =>
    Ok
  }
}