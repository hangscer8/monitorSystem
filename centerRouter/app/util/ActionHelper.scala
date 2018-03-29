package util

import java.util.Date

import play.api.mvc._

trait ActionHelper {
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
}