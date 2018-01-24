package nathan

import java.lang.management.ManagementFactory

import com.sun.management.OperatingSystemMXBean
import nathan.monitorSystem.Protocols.CPUPerc

object OS {

  import nathan.sigarUtil.SigarUtils.sigar

  val osBean = ManagementFactory.getPlatformMXBean(classOf[OperatingSystemMXBean])

  def getCPUPerc(): CPUPerc = {
    val cpuPerc = sigar.getCpuPerc
    CPUPerc(user = cpuPerc.getUser, sys = cpuPerc.getUser, _wait = cpuPerc.getWait, idle = cpuPerc.getIdle, combined = cpuPerc.getCombined)
  }
}

// uname -a
//top -l 1
//top -b -n 1
// * 0次 1次 多次
// ? 0次 1次
// + 1次 多次