package nathan.router

import akka.http.scaladsl.server.Directives._
import nathan.monitorSystem.AkkaSystemConst._
import nathan.monitorSystem.Protocols.RegisterReq
import nathan.service.user.RegisterTrait
import nathan.util.JsonUtil._
import nathan.util.JsonUtil._

class RegisterRouter extends RegisterTrait with BaseRouterTrait {
  val route = pathPrefix(prefix) {
    apiAuthentication { auth =>
      path("register" / "userName" / Segment) { userName => //该用户名是否可用
        get {
          complete(isUserNameExits(userName))
        }
      } ~
        path("register") {
          entity(as[RegisterReq]) { req =>
            post {
              complete(registerUser(req))
            }
          }
        }
    }
  }
}