package nathan.router
import akka.http.scaladsl.server.Directives.pathPrefix
import nathan.util.JsonUtil._
class UserRouter extends BaseRouterTrait{
  val route = pathPrefix(Protocol.prefix) {
    apiAuthentication(auth){

    }
  }
}
