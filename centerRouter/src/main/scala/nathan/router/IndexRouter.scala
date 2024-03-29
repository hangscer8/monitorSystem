package nathan.router

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import nathan.protocol.Protocol.Agent
import nathan.util.JsonUtil._
import nathan.monitorSystem.AkkaSystemConst._
class IndexRouter extends BaseRouterTrait {

  val route = pathPrefix(prefix) {
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