package nathan

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import nathan.dbentity.DbInit
import nathan.ec.ExecutorService
import nathan.router._
import nathan.util.CorsSupport
import nathan.monitorSystem.AkkaSystemConst._

object CenterRouterMain extends App with CorsSupport {
  implicit val system = ExecutorService.system
  implicit val mat = ExecutorService.mat
  implicit val ec = ExecutorService.ec
  DbInit.init

  def route: Route = {
    val indexRouter = new IndexRouter()
    val userRouter = new UserRouter()
    val cpuPercRouter = new CPUPercRouter()
    val registerRouter = new RegisterRouter
    val agentRouter = new AgentRouter()
    val route = indexRouter.route ~ userRouter.route ~ cpuPercRouter.route ~ registerRouter.route ~ agentRouter.route
    corsHandler(route)
  }
  
  Http().bindAndHandle(route, akkaServerIp, akkaServerPort)
}