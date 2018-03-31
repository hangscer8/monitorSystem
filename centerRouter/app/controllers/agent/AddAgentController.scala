package controllers.agent

import javax.inject._
import io.circe.generic.auto._
import nathan.monitorSystem.Protocols.AddAgentReq
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import service.agent.AgentServiceTrait
import util.ActionContext
import util.ExecutorService._

@Singleton
class AddAgentController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with AgentServiceTrait {
  def index = ActionContext.imperativelyComplete { ctx =>
    ctx.complete(Ok(views.html.agent.addAgent("添加AGENT")))
  }

  def addAgent() = ActionContext.imperativelyComplete(circe.json[AddAgentReq]) { ctx =>
    supervisorActor ! (ctx, ctx.request.body)
  }
}