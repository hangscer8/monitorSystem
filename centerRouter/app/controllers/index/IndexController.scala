package controllers.index

import javax.inject._

import play.api.libs.circe.Circe
import play.api.mvc._
import service.index.IndexServiceTrait
import util.{ActionContext, ActionHelper}

@Singleton
class IndexController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with ActionHelper with IndexServiceTrait {
  //  def index() = LoginAction { request =>
  //    Ok(views.html.index.index("首页"))
  //  }
  def index = ActionContext.imperativelyComplete { ctx =>
    println(ctx.request)
    Thread.sleep(2000)
    ctx.complete(Ok(views.html.index.index("首页")))
  }
}