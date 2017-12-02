package nathan.util

import akka.http.scaladsl.model.HttpMethods.{DELETE, GET, POST, PUT}
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.server.Directives.{complete, _}
import akka.http.scaladsl.server.{Directive0, Route}
import nathan.router.Protocol

trait CorsSupport {
  def addAccessControlHeaders: Directive0 = mapResponseHeaders { headers =>
    `Access-Control-Allow-Origin`.* +:
      `Access-Control-Allow-Headers`("Authorization", Protocol.auth, "Content-Type", "X-Requested-With", "Origin", "X-Requested-With", "Accept", "Accept-Encoding", "Accept-Language", "Host", "Referer", "User-Agent", "kbn-version") +:
      headers
  }

  def preflightRequestHandler:Route = options {
    complete(HttpResponse(OK).withHeaders(`Access-Control-Allow-Methods`(POST, GET, DELETE, PUT)))
  }

  def corsHandler(r: Route) = addAccessControlHeaders {
    preflightRequestHandler ~ r
  }
}
