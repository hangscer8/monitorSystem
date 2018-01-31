package nathan.router

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import nathan.monitorSystem.AkkaSystemConst
import nathan.protocol.Protocol.Agent
import nathan.util.JsonUtil._

class IndexRouter extends BaseRouterTrait with AkkaSystemConst{

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