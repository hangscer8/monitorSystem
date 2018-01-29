package nathan

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import nathan.dbentity.DbInit
import nathan.ec.ExecutorService
import nathan.router.{IndexRouter, UserRouter}
import nathan.util.CorsSupport

object Main extends App with CorsSupport {
  implicit val system = ExecutorService.system
  implicit val mat = ExecutorService.mat
  implicit val ec = ExecutorService.ec
  DbInit.init

  def route: Route = {
    val indexRouter = new IndexRouter()
    val userRouter = new UserRouter()
    val route = indexRouter.route ~ userRouter.route
    corsHandler(route)
  }
  
}