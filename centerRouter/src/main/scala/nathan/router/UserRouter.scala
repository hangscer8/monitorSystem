package nathan.router

import akka.http.scaladsl.server.Directives.{path, pathPrefix, _}
import nathan.ec.ExecutorService
import nathan.monitorSystem.AkkaSystemConst
import nathan.protocol.Protocol.{LoginReq, UserReq}
import nathan.service.user.UserTrait
import nathan.util.JsonUtil._

class UserRouter extends BaseRouterTrait with UserTrait with AkkaSystemConst{
  implicit val ec = ExecutorService.ec
  val route = pathPrefix(prefix) {
    apiAuthentication { auth =>
      path("user") {
        post {
          entity(as[UserReq]) { userReq =>
            complete(createUser(userReq, auth))
          }
        }
      } ~
        path("login") {
          post {
            entity(as[LoginReq]) { loginReq =>
              complete(login(loginReq, auth))
            }
          }
        }
    }
  }
}
