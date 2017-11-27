package nathan.os

case class ProcessesInfo(total: Int, running: Int, stuck: Int, sleeping: Int, threads: Int) //得到相关数量
case class CollectionTimeInfo(year: Int, month: Int, day: Int, hour: Int, min: Int, sec: Int)

//24小时制 收集的时间
case class LoadAvg(every1Min: Double, every5Min: Double, every15Min: Double)

case class CPUUsage(user: Double, sys: Double, idle: Double)

case class PhysMem(used: Int, wired: Int, unused: Int) //PhysMem: 7741M used (1421M wired), 449M unused.
case class JpsItem(pid: Int, name: String)