package nathan.router

import akka.http.scaladsl.server.Directives._
import nathan.router.Protocol.Agent

class IndexRouter extends BaseRouterTrait {

  val route = {
   val r= pathPrefix(Protocol.prefix) {
      apiAuthentication { auth =>
        path("agent") {
          post {
            entity(as[Agent]) { req =>
              println(req)
              complete(auth + ":asdas")
            }
          }
        }
      }
    }
    corsHandler(r)
  }
}
