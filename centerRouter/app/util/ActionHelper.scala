package util

import java.util.Date

import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration._

trait ActionHelper {
  private[this] val loginKey = "isLogin"

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