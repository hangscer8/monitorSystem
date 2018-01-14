package nathan

import java.io.File
import java.lang.management.ManagementFactory

import com.sun.management.{OperatingSystemMXBean, UnixOperatingSystemMXBean}
import nathan.Protocols.monitorJdk.{JHeap, JpsItem, Usege}
import nathan.Protocols.monitorSystem._

import scala.sys.process._

trait OS

case object OSX extends OS

case object Linux extends OS

object OS {
  val osBean = ManagementFactory.getPlatformMXBean(classOf[OperatingSystemMXBean])

  def whichOS: OS = {
    "uname".!!.trim match { // must "Darwin\n" trim去掉换行
      case "Darwin" => OSX
      case "Linux" => Linux
    }
  }

  def top(os: OS = OSX) = os match {
    case OSX => "top -l 1".!!.trim
    case Linux => "top -b -n 1".!!.trim
  }

  def `jps -l`: List[JpsItem] = {
    val Extract ="""(\d+)\s+(.*)\s*""".r
    "jps -l".!!.trim.split("\n").map { line =>
      val Extract(pid, name) = line
      JpsItem(pid.toInt, name)
    }.toList
  }

  def `jmap -heap pid`(pid: Int) = {
    val list = s"jmap -heap $pid".!!.trim.split("\n").toList.map(_.trim).filter(_.nonEmpty)
    val _edenArray = list.dropWhile(!_.startsWith("Eden Space")).takeWhile(!_.startsWith("From Space")).toArray
    val _fromArray = list.dropWhile(!_.startsWith("From Space")).takeWhile(!_.startsWith("To Space")).toArray
    val _toArray = list.dropWhile(!_.startsWith("To Space")).toArray
    val _oldArray = list.dropWhile(!_.contains("PS Old")).toArray
    val array = Array(_edenArray, _fromArray, _toArray, _oldArray)

    val Capacity = """capacity\s*=\s*(\d+)\s*\(.*\)""".r
    val Used = """used\s*=\s*(\d+)\s*\(.*\)""".r
    val Free ="""free\s*=\s*(\d+)\s*\(.*\)""".r

    val temp = array.map { space =>
      val Capacity(cap) = space(1)
      val Used(used) = space(2)
      val Free(free) = space(3)
      Usege(cap.toLong, used.toLong, free.toLong)
    }
    JHeap(temp(0), temp(1), temp(2), temp(3))
  }

  def systemInfo() = {
    val systemLoadAvg = SystemLoadAvg(osBean.getSystemLoadAverage / osBean.getAvailableProcessors)
    val cpuLoad = CPULoad(osBean.getSystemCpuLoad)
    val phyMem = PhyMem(osBean.getTotalPhysicalMemorySize.toDouble / 1024 / 1024 / 1024, osBean.getFreePhysicalMemorySize.toDouble / 1024 / 1024 / 1024)
    val swapMem = SwapMem(osBean.getTotalSwapSpaceSize.toDouble / 1024 / 1024 / 1024, osBean.getFreeSwapSpaceSize.toDouble / 1024 / 1024 / 1024)
    val fileSpace = FileSpace(total = File.listRoots().foldRight(0.0)((root, total) => root.getTotalSpace + total), free = File.listRoots().foldRight(0.0)((root, free) => root.getFreeSpace + free))
    val fileDescriptor = (osBean match {
      case _osBean: UnixOperatingSystemMXBean => Option((_osBean.getMaxFileDescriptorCount, _osBean.getOpenFileDescriptorCount))
    }).map(tuple => FileDescriptor(max = tuple._1, opened = tuple._2)).get
  }
}

// uname -a
//top -l 1
//top -b -n 1
// * 0次 1次 多次
// ? 0次 1次
// + 1次 多次