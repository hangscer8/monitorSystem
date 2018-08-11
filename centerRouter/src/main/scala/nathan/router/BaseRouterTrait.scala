package nathan.router

import akka.http.scaladsl.model.StatusCodes.CustomStatusCode
import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives.{complete, _}
import nathan.monitorSystem.AkkaSystemConst._
import nathan.util.UserSupport

trait BaseRouterTrait {
  val withOutLogin = CustomStatusCode(403)(reason = "没有登陆", defaultMessage = "忘记登陆了吧", isSuccess = true, allowsEntity = true)
  val withOutAuthHead=CustomStatusCode(403)(reason = "没有authHead", defaultMessage = "没有authHead", isSuccess = true, allowsEntity = true)
  def apiAuthentication: Directive1[Option[String]] = {
    extractRequest.flatMap { case request =>
      val url = request.uri.path.toString() //request.uri.toString => request.uri.path.toString 一个天大的bug
      url match {
        case "/monitorSystem/login" =>
          optionalHeaderValueByName(authHead).flatMap {
            case Some(auth) =>
              provide(Some(auth))
            case None =>
              complete(withOutAuthHead)
          }
        case _ =>
          List(
            "/monitorSystem/register"
          ).exists(pathStr => url.startsWith(pathStr)) match { //哪些url不需要验证登陆
            case true =>
              provide(None)
            case false =>
              optionalHeaderValueByName(authHead).flatMap {
                case Some(auth) =>
                  UserSupport.isLoginUser(Some(auth)) match { //全部可以登陆(不用登陆)
                    case false =>
                      complete(withOutLogin)
                    case _ =>
                      provide(Some(auth))
                  }
                case None =>
                  parameterMap.flatMap { paramsKV => //从get参数中获取
                    paramsKV.get(authHead) match {
                      case Some(auth) =>
                        UserSupport.isLoginUser(Some(auth)) match {
                          case true =>
                            provide(Some(auth))
                          case false =>
                            complete(withOutLogin)
                        }
                      case None =>
                        complete(withOutLogin)
                    }
                  }
              }
          }

      }
    }
  }
}
