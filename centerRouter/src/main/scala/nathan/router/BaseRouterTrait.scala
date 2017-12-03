package nathan.router

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives.{complete, _}

trait BaseRouterTrait {

  def apiAuthentication: Directive1[Option[String]] = {
    extractRequest.flatMap { case request =>
      request.uri.toString() match {
        case "/monitorSystem/login" =>
          optionalHeaderValueByName(Protocol.auth).flatMap{
            case Some(auth)=>
              provide(Some(auth))
            case None =>
              complete(StatusCodes.BadRequest)
          }
        case _ =>
          optionalHeaderValueByName(Protocol.auth).flatMap {
            case Some(id) =>
              provide(Some(id))
            case None =>
              parameterMap.flatMap { paramsKV => //从get参数中获取
                paramsKV.get(Protocol.auth) match {
                  case Some(id) =>
                    provide(Some(id))
                  case None =>
                    provide(None)
                }
              }
          }
      }
    }
  }
}
