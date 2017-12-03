package nathan.router
import akka.http.scaladsl.server.Directives.{path, pathPrefix}
import nathan.util.JsonUtil._
import akka.http.scaladsl.server.Directives._
import nathan.protocol.Protocol.UserReq
class UserRouter extends BaseRouterTrait{
  val route = pathPrefix(Protocol.prefix) {
    apiAuthentication{auth=>
      path("monitorSystem" / "login"){
        post{
          entity(as[UserReq]){userReq=>
            complete("haha:"+auth+" ;;"+userReq)
          }
        }
      }
    }
  }
}
