package util

import java.util.Date

import play.api.mvc._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future, Promise}
import scala.util.Try
import util.ExecutorService._

trait ActionHelper extends UtilTrait {

  def LoggingAction(f: Request[AnyContent] => Result): Action[AnyContent] = {
    Action { implicit request =>
      println(s"timestamp:${new Date(System.currentTimeMillis())} , method:${Console.RED}${request.method.toUpperCase}${Console.RESET} , body:${request.body} , headers:${request.headers} ,path:${Console.GREEN}${request.path}${Console.RESET} , session:${Console.BLUE}${request.session}${Console.RESET} , flash:${Console.MAGENTA}${request.flash}${Console.RESET}")
      f(request)
    }
  }

  def LoggingAction[A](bp: BodyParser[A])(f: Request[A] => Result): Action[A] = {
    Action(bp) { implicit request =>
      println(s"timestamp:${new Date(System.currentTimeMillis())} , method:${Console.RED}${request.method.toUpperCase}${Console.RESET} , body:${request.body}, headers:${request.headers} ,path:${Console.GREEN}${request.path}${Console.RESET} , session:${Console.BLUE}${request.session}${Console.RESET} , flash:${Console.MAGENTA}${request.flash}${Console.RESET}")
      f(request)
    }
  }

  def LoginAction(f: Request[AnyContent] => Result): Action[AnyContent] = {
    Action { implicit request =>
      request.session.get(loginKey) match {
        case None => Results.Unauthorized
        case _ => Await.result(LoggingAction(f)(request), 40 seconds)
      }
    }
  }

  def LoginAction[A](bp: BodyParser[A])(f: Request[A] => Result): Action[A] = {
    LoggingAction(bp)(f)
    Action(bp) { implicit request =>
      request.session.get(loginKey) match {
        case None => Results.Unauthorized
        case _ => Await.result(LoggingAction(bp)(f)(request), 40 seconds)
      }
    }
  }
}

class ActionContext[A](val request: Request[A], private[this] val p: Promise[Result]) {
  def complete(result: Result): Future[Result] = {
    p.complete(Try(result))
    p.future
  }
}

object ActionContext {
  def imperativelyComplete[A](bp: BodyParser[A])(inner: ActionContext[A] => Unit): Action[A] = {
    Action.async(bp) { request =>
      val p = Promise[Result]
      inner(new ActionContext(request, p))
      p.future
    }
  }

  def imperativelyComplete(inner: ActionContext[AnyContent] => Unit): Action[AnyContent] = {
    Action.async { request =>
      val p = Promise[Result]
      inner(new ActionContext(request, p))
      p.future
    }
  }
}