package nathan

import nathan.sigarUtil.SigarUtils.sigar

object Main extends App {
  println(sigar.getCpuInfoList.toList)
  println(sigar.getNetInfo)
  println(sigar.getCpuPerc)
}