package nathan.ec

import java.io.File
import java.lang.management.ManagementFactory

import com.sun.management.OperatingSystemMXBean
import com.typesafe.config.ConfigFactory
import nathan.monitorSystem.Protocols._
import nathan.util.Snowflake
import oshi.SystemInfo

object OS {

  val si = new SystemInfo()
  val hw = si.getHardware
  val osBean = ManagementFactory.getPlatformMXBean(classOf[OperatingSystemMXBean])
  val cpuNum = osBean.getAvailableProcessors

  def getCPUPerc(agentId: String): CPUPercEntity = {
    val (_user, _system, _idle, total) = hw.getProcessor.getProcessorCpuLoadTicks.foldLeft((0L, 0L, 0L, 0L)) { case ((user, sys, idle, total), loadArray) =>
      val _user :: _nice :: _system :: _idle :: _iOwait :: _iRQ :: _softIRQ :: _steal :: Nil = loadArray.toList
      (user + _user, sys + _system, idle + _idle, total + (_user + _nice + _system + _idle + _iOwait + _iRQ + _softIRQ + _steal))
    }
    CPUPercEntity(Snowflake.nextId(), user = _user * 1.0 / total, sys = _system * 1.0 / total, idle = _idle * 1.0 / total, agentId = agentId)
  }

  def getMEM(agentId: String): MEMEntity = {
    val mem = hw.getMemory
    MEMEntity(Snowflake.nextId(), total = mem.getTotal * 1.0 / 1024 / 1024, used = (mem.getTotal - mem.getAvailable) * 1.0 / 1024 / 1024, agentId = agentId)
  }

  def getSwap(agentId: String): SWAPEntity = {
    val swap = hw.getMemory
    SWAPEntity(Snowflake.nextId(), total = swap.getTotal * 1.0 / 1024 / 1024, used = swap.getSwapUsed * 1.0 / 1024 / 1024, agentId = agentId)
  }

  def getLoadAvg(agentId: String): LoadAvgEntity = {
    val avg = hw.getProcessor.getSystemLoadAverage(3)
    LoadAvgEntity(Snowflake.nextId(), `1min` = avg(0) / cpuNum, `5min` = avg(1) / cpuNum, `15min` = avg(2) / cpuNum, agentId = agentId)
  }

  def getFileUsage(agentId: String): FileUsageEntity = {
    val total = File.listRoots().foldRight(0L)((root, z) => z + root.getTotalSpace)
    val free = File.listRoots().foldRight(0L)((root, z) => z + root.getFreeSpace)
    FileUsageEntity(Snowflake.nextId(), total = total * 1.0 / 1024 / 1024 / 1024, used = (total - free) * 1.0 / 1024 / 1024 / 1024, agentId = agentId)
  }

  def getNetInfo(agentId: String): NetInfoEntity = {
    val netInterface = hw.getNetworkIFs
    netInterface.foldRight(NetInfoEntity(Snowflake.nextId(), netSpeed = 0L, agentId = agentId)) { (interface, netInfo) =>
      NetInfoEntity(Snowflake.nextId(), netSpeed = netInfo.netSpeed + (interface.getSpeed) / 8 / 1024, agentId = agentId)
    }
  }

  def getAgentInfo(agentId: String) = {
    val config = ConfigFactory.load()
    val ip = config.getString("akka.remote.netty.tcp.hostname")
    val akkaPort = config.getInt("akka.remote.netty.tcp.port")
    val cpuInfo = hw.getProcessor
    si.getHardware
    AgentMachineEntity(Snowflake.nextId(), ip = ip, akkaPort = akkaPort, agentId = agentId, cpuVendor = cpuInfo.getVendor, model = cpuInfo.getModel, sendMsgNum = 0L, joinedTime = System.currentTimeMillis())
  }
}

// uname -a
//top -l 1
//top -b -n 1
// * 0次 1次 多次
// ? 0次 1次
// + 1次 多次