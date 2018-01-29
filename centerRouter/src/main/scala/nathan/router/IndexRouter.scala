package nathan.router

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import nathan.protocol.Protocol.Agent

class IndexRouter extends BaseRouterTrait {

  val route = pathPrefix("monitorSystem") {
    apiAuthentication { auth =>
      path("agent") {
        post {
          entity(as[Agent]) { req =>
            println(req)
            complete(HttpEntity(ContentTypes.`application/json`, """{"a":1}"""))
          }
        }
      }
    }
  }
}