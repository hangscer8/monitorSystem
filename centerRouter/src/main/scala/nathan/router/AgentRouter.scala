package nathan.router

import akka.http.scaladsl.server.Directives._
import nathan.ec.ExecutorService._
import nathan.monitorSystem.AkkaSystemConst._
import nathan.service.agent.AgentService
import nathan.util.ImperativeRequestContext._

class AgentRouter extends BaseRouterTrait with AgentService {
  val route = pathPrefix(prefix) {
    apiAuthentication { auth =>
      path("agent") {
        parameter("ip".as[String], "port".as[Int]) { (ip, port) =>
          imperativelyComplete { ctx =>
            centerRouterActor ! (ctx, ip, port)
          }
        }
      }
    }
  }
}