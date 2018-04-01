package controllers.agent

import entity.EntityTable.db
import javax.inject._
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import service.agent.AgentServiceTrait
import util.{ActionContext, UtilTrait}

@Singleton
class ShowAgentController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with AgentServiceTrait with UtilTrait {
  def index() = ActionContext.imperativelyComplete { ctx =>
    ctx.complete(Ok(views.html.agent.showAgent("AGENT展示", db.run(listAgentDBIO).exe)))
  }
}