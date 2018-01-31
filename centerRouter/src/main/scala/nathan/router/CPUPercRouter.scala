package nathan.router

import akka.http.scaladsl.server.Directives._
import nathan.monitorSystem.AkkaSystemConst
import nathan.service.cpu.CpuPercDao
import nathan.util.JsonUtil._
class CPUPercRouter extends BaseRouterTrait with CpuPercDao with AkkaSystemConst{
  val route = pathPrefix(prefix) {
    apiAuthentication { auth =>
      path("cpuPerc") {
        parameter("agentId".as[String], "start".as[Long] ?, "end".as[Long] ?) { (agentId, startOpt, endOpt) =>
          complete(getCPUPerc(agentId, startOpt, endOpt))
        }
      }
    }
  }
}