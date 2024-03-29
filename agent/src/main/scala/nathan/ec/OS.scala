package nathan.ec

import java.io.File
import java.lang.management.ManagementFactory

import com.sun.management.OperatingSystemMXBean
import com.typesafe.config.ConfigFactory
import nathan.monitorSystem.Protocols._
import nathan.util.{AgentConf, SigarUtil, Snowflake}

object OS {

  val sigar = SigarUtil.sigarInstance
  val osBean = ManagementFactory.getPlatformMXBean(classOf[OperatingSystemMXBean])
  val cpuNum = osBean.getAvailableProcessors

  def getCPUPerc(agentId: String): CPUPercEntity = {
    val cpuPerc = sigar.getCpuPerc
    CPUPercEntity(Snowflake.nextId(), user = cpuPerc.getUser, sys = cpuPerc.getSys, _wait = cpuPerc.getWait, idle = cpuPerc.getIdle, combined = cpuPerc.getCombined, agentId = agentId)
  }

  def getMEM(agentId: String): MEMEntity = {
    val mem = sigar.getMem
    MEMEntity(Snowflake.nextId(), total = mem.getTotal * 1.0 / 1024 / 1024, used = mem.getUsed * 1.0 / 1024 / 1024, agentId = agentId)
  }

  def getSwap(agentId: String): SWAPEntity = {
    val swap = sigar.getSwap
    SWAPEntity(Snowflake.nextId(), total = swap.getTotal * 1.0 / 1024 / 1024, used = swap.getUsed * 1.0 / 1024 / 1024, agentId = agentId)
  }

  def getLoadAvg(agentId: String): LoadAvgEntity = {
    val avg = sigar.getLoadAverage
    LoadAvgEntity(Snowflake.nextId(), `1min` = avg(0) / cpuNum, `5min` = avg(1) / cpuNum, `15min` = avg(2) / cpuNum, agentId = agentId)
  }

  def getFileUsage(agentId: String): FileUsageEntity = {
    val total = File.listRoots().foldRight(0L)((root, z) => z + root.getTotalSpace)
    val free = File.listRoots().foldRight(0L)((root, z) => z + root.getFreeSpace)
    FileUsageEntity(Snowflake.nextId(), total = total * 1.0 / 1024 / 1024 / 1024, used = (total - free) * 1.0 / 1024 / 1024 / 1024, agentId = agentId)
  }

  def getAgentInfo(agentId: String) = {
    val config = AgentConf.applicationConf
    val ip = config.getString("akka.remote.netty.tcp.hostname")
    val akkaPort = config.getInt("akka.remote.netty.tcp.port")
    val cpuInfo = sigar.getCpuInfoList.head
    AgentMachineEntity(ip = ip, akkaPort = akkaPort, agentId = agentId, cpuVendor = cpuInfo.getVendor, model = cpuInfo.getModel, coresPerCpu = cpuInfo.getCoresPerSocket, sendMsgNum = 0L, joinedTime = System.currentTimeMillis(), lastReceiveMsgTime = System.currentTimeMillis(), isOnLine = 1)
  }
}

// uname -a
//top -l 1
//top -b -n 1
// * 0次 1次 多次
// ? 0次 1次
// + 1次 多次