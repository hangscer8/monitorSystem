package nathan

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import nathan.dbentity.DbInit
import nathan.ec.ExecutorService
import nathan.router.IndexRouter
import nathan.util.CorsSupport
object Main extends App with CorsSupport{
  implicit val system = ExecutorService.system
  implicit val mat = ExecutorService.mat
  implicit val ec = ExecutorService.ec
  DbInit.init
  def route: Route = {
    val indexRouter = new IndexRouter()
    val route=indexRouter.route
    corsHandler(route)
  }
  Http().bindAndHandle(route, "127.0.0.1", 8888)
}