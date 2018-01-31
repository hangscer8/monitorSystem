package nathan.util

import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.server.Directives.{complete, _}
import akka.http.scaladsl.server.{Directive0, Route}
import nathan.monitorSystem.AkkaSystemConst

trait CorsSupport extends AkkaSystemConst{
  def addAccessControlHeaders: Directive0 = mapResponseHeaders { headers =>
    `Access-Control-Allow-Headers`("Authorization", "MONITORSYSTEM-AUTH", "Content-Type", "X-Requested-With", "Origin", "X-Requested-With", "Accept", "Accept-Encoding", "Accept-Language", "Host", "Referer", "User-Agent", "kbn-version") +:
      `Access-Control-Allow-Origin`.* +:
      headers
  }

  def preflightRequestHandler: Route = options {
    complete(
      HttpResponse(OK)
        .withHeaders(`Access-Control-Allow-Methods`(POST, GET, DELETE, PUT, OPTIONS))
    )
  }

  def corsHandler(r: Route) = addAccessControlHeaders {
    preflightRequestHandler ~ r
  }
}
