package nathan

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import nathan.ec.ExecutorService
import nathan.router.IndexRouter

object Main extends App {
  implicit val system = ExecutorService.system
  implicit val mat = ExecutorService.mat
  implicit val ec = ExecutorService.ec

  def route: Route = {
    val indexRouter = new IndexRouter()
     indexRouter.route
  }

  Http().bindAndHandle(route, "127.0.0.1", 8888)
}