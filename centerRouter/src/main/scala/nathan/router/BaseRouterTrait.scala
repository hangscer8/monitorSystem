package nathan.router

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.directives.BasicDirectives._
import akka.http.scaladsl.server.directives.HeaderDirectives._
import akka.http.scaladsl.server.directives.ParameterDirectives._
import akka.http.scaladsl.server.directives.RouteDirectives._
trait BaseRouterTrait extends JsonSupport{
  def apiAuthentication: Directive1[Option[String]] = {
    extractRequest.flatMap { case request =>
      request.uri.toString() match {
        case "/monitorSystem/admin/login" | "/monitorSystem/user/login" =>
          provide(None)
        case "/hah" => //非法访问
          complete(StatusCodes.BadRequest)
        case _ =>
          optionalHeaderValueByName(Protocol.auth).flatMap{
            case Some(id)=>
              provide(Some(id))
            case None =>
              parameterMap.flatMap{paramsKV=> //从get参数中获取
                paramsKV.get(Protocol.auth) match {
                  case Some(id)=>
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
