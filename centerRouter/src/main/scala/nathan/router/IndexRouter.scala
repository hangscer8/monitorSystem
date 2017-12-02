package nathan.router

import akka.http.scaladsl.server.Directives._
import nathan.router.Protocol.Agent

class IndexRouter extends BaseRouterTrait {

  val route = pathPrefix(Protocol.prefix) {
    apiAuthentication { auth =>
      path("agent") {
        post {
          entity(as[Agent]) { req =>
            println(req)
            complete(List(Map("1"->req)))
          }
        }
      }
    }
  }

}
