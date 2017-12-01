package nathan

import nathan.dbentity.EntityTable._
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import nathan.ec.ExecutorService
import nathan.router.IndexRouter
import slick.jdbc.H2Profile.api._
import scala.concurrent.duration._
import scala.concurrent.Await

object Main extends App {
  implicit val system = ExecutorService.system
  implicit val mat = ExecutorService.mat
  implicit val ec = ExecutorService.ec

  def route: Route = {
    val indexRouter = new IndexRouter()
    indexRouter.route
  }

  Http().bindAndHandle(route, "127.0.0.1", 8888)
  val db = Database.forConfig("h2local")
  val r=Await.result(db.run(users.schema.create.asTry), 10 seconds) //重复创建 已经存在的话，就出错
  
}