package controllers.index

import javax.inject._

import play.api.libs.circe.Circe
import play.api.mvc._
import service.index.IndexServiceTrait
import util.{ActionContext}

@Singleton
class IndexController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with IndexServiceTrait {
  def index = ActionContext.imperativelyComplete { ctx =>
    ctx.complete(Ok(views.html.index.index("首页")))
  }
}