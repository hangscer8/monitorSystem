package util

import play.api.mvc._

import scala.concurrent.{Future, Promise}
import scala.util.Try

class ActionContext[A](val request: Request[A], private[this] val p: Promise[Result]) {
  def complete(result: Result): Future[Result] = {
    p.complete(Try(result))
    p.future
  }
}

object ActionContext {
  def imperativelyComplete[A](bp: BodyParser[A])(inner: ActionContext[A] => Unit): Action[A] = {
    Action.async(bp) { implicit request =>
      val p = Promise[Result]
      inner(new ActionContext(request, p))
      p.future
    }
  }

  def imperativelyComplete(inner: ActionContext[AnyContent] => Unit): Action[AnyContent] = {
    Action.async { implicit request =>
      val p = Promise[Result]
      inner(new ActionContext(request, p))
      p.future
    }
  }
}