package nathan.util

import akka.http.scaladsl.model.HttpMethods.{DELETE, GET, OPTIONS, POST, PUT}
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.model.headers.CacheDirectives.{`max-age`, `no-cache`}
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.server.Directives.{complete, _}
import akka.http.scaladsl.server.{Directive0, Route}
import nathan.router.Protocol

trait CorsSupport {
  def addAccessControlHeaders: Directive0 = mapResponseHeaders { headers =>
    `Access-Control-Allow-Origin`.* +: `Cache-Control`(`no-cache`,`max-age`(0)) +: headers
  }
  def preflightRequestHandler: Route = options {
    complete(HttpResponse(OK).withHeaders(`Access-Control-Allow-Methods`(POST, GET, DELETE, PUT, OPTIONS)))
  }

  def corsHandler(r: Route) = addAccessControlHeaders {
    preflightRequestHandler ~ r
  }
}
