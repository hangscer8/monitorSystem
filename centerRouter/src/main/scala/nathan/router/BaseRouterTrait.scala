package nathan.router

import akka.http.scaladsl.model.HttpMethods.{DELETE, GET, POST, PUT}
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, _}
import akka.http.scaladsl.server.directives.MethodDirectives
import akka.http.scaladsl.server.{Directive0, Directive1, Route}

trait BaseRouterTrait extends JsonSupport {
  def addAccessControlHeaders: Directive0 = mapResponseHeaders { headers =>
    `Access-Control-Allow-Origin`.* +:
      `Access-Control-Allow-Headers`("Authorization", Protocol.auth, "Content-Type", "X-Requested-With", "Origin", "X-Requested-With", "Accept", "Accept-Encoding", "Accept-Language", "Host", "Referer", "User-Agent", "kbn-version") +:
      headers
  }

  def preflightRequestHandler = MethodDirectives.options {
    complete(HttpResponse(OK).withHeaders(`Access-Control-Allow-Methods`(POST, GET, DELETE, PUT)))
  }

  def corsHandler(r: Route) = addAccessControlHeaders {
    preflightRequestHandler ~ r
  }

  def apiAuthentication: Directive1[Option[String]] = {
    extractRequest.flatMap { case request =>
      request.uri.toString() match {
        case "/monitorSystem/admin/login" | "/monitorSystem/user/login" =>
          provide(None)
        case "/hah" => //非法访问
          complete(StatusCodes.BadRequest)
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
