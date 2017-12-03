package nathan.router

import akka.http.scaladsl.server.Directives.{path, pathPrefix}
import nathan.util.JsonUtil._
import akka.http.scaladsl.server.Directives._
import nathan.ec.ExecutorService
import nathan.protocol.Protocol.UserReq
import nathan.service.user.UserTrait

class UserRouter extends BaseRouterTrait with UserTrait {
  implicit val ec = ExecutorService.ec
  val route = pathPrefix("monitorSystem") {
    apiAuthentication { auth =>
      path("login") {
        post {
          entity(as[UserReq]) { userReq =>
            complete(createUser(userReq, auth))
          }
        }
      }
    }
  }
}
