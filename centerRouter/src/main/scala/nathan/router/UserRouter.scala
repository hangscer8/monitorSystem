package nathan.router

import akka.http.scaladsl.server.Directives.{path, pathPrefix, _}
import nathan.ec.ExecutorService
import nathan.monitorSystem.AkkaSystemConst._
import nathan.protocol.Protocol.{LoginReq, UserReq}
import nathan.service.user.UserTrait
import nathan.util.JsonUtil._

class UserRouter extends BaseRouterTrait with UserTrait {
  implicit val ec = ExecutorService.ec
  val route = pathPrefix(prefix) {
    apiAuthentication { auth =>
      path("user") {
        post {
          entity(as[UserReq]) { userReq =>
            complete(createUser(userReq, auth))
          }
        } ~
          get {
            parameter("userName".as[String]) { userName =>
              complete("")
            }
          }
      }
    }
  }
}
