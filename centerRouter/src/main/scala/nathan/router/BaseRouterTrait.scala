package nathan.router

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives.{complete, _}
import nathan.util.UserSupport

trait BaseRouterTrait {

  def apiAuthentication: Directive1[Option[String]] = {
    extractRequest.flatMap { case request =>
      request.uri.toString() match {
        case "/monitorSystem/login" =>
          optionalHeaderValueByName(Protocol.authHead).flatMap{
            case Some(auth)=>
              provide(Some(auth))
            case None =>
              complete(StatusCodes.Forbidden)
          }
        case _ =>
          optionalHeaderValueByName(Protocol.authHead).flatMap {
            case Some(auth) =>
              UserSupport.isLoginUser(Some(auth)) match {
                case false =>
                  complete(StatusCodes.Forbidden)
                case _ =>
                  provide(Some(auth))
              }
            case None =>
              parameterMap.flatMap { paramsKV => //从get参数中获取
                paramsKV.get(Protocol.authHead) match {
                  case Some(auth) =>
                    provide(Some(auth))
                  case None =>
                    complete(StatusCodes.Forbidden)
                }
              }
          }
      }
    }
  }
}
