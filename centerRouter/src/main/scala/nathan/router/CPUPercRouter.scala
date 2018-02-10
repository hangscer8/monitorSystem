package nathan.router

import akka.http.scaladsl.server.Directives._
import nathan.monitorSystem.AkkaSystemConst._
import nathan.service.cpu.CpuPercDao
import nathan.util.JsonUtil._

class CPUPercRouter extends BaseRouterTrait with CpuPercDao {
  val route = pathPrefix(prefix) {
    apiAuthentication { auth =>
      path("cpuPerc") {
        parameter("agentId".as[String], "size".as[Long] ) { (agentId, size) =>
          complete(getCPUPerc(agentId, size))
        }
      }
    }
  }
}