package nathan.Protocols
package monitorMac {

  case class ProcessesInfo(total: Int, running: Int, stuck: Int, sleeping: Int, threads: Int) //得到相关数量
  case class CollectionTimeInfo(year: Int, month: Int, day: Int, hour: Int, min: Int, sec: Int)

  //24小时制 收集的时间
  case class LoadAvg(every1Min: Double, every5Min: Double, every15Min: Double, cpuNum: Int)

  case class CPUUsage(user: Double, sys: Double, idle: Double)

  case class PhysMem(used: Int, wired: Int, unused: Int) //PhysMem: 7741M used (1421M wired), 449M unused.
}

package monitorSystem {

  case class SystemLoadAvg(every1Min: Double) //0.0-1.0,除以cpu核数
  case class CPULoad(load: Double) //0.0-1.0，如果无法获取，即NaN
  case class PhyMem(total: Double, free: Double) // 单位GB 总共、空闲
  case class SwapMem(total: Double, free: Double) //单位GB 总共、空闲
  case class FileSpace(total: Double, free: Double) //单位 GB 总共、空闲
  case class FileDescriptor(max: Long, opened: Long) //最大可打开的文件描述符 已打开的

}

package monitorJdk {

  case class JpsItem(pid: Int, name: String)

  case class Usege(capacity: Long, used: Long, free: Long) //Byte
  case class JHeap(eden: Usege, from: Usege, to: Usege, old: Usege)

}