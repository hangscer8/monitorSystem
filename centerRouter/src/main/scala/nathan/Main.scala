package nathan

import nathan.dbentity.EntityTable._
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import nathan.ec.ExecutorService
import nathan.router.IndexRouter

import scala.concurrent.duration._
import scala.concurrent.Await
import h2.api._
import nathan.dbentity.DbInit
object Main extends App {
//  implicit val system = ExecutorService.system
//  implicit val mat = ExecutorService.mat
//  implicit val ec = ExecutorService.ec
  DbInit.init
//  def route: Route = {
//    val indexRouter = new IndexRouter()
//    indexRouter.route
//  }
  Thread.sleep(4000)
//  Http().bindAndHandle(route, "127.0.0.1", 8888)
}