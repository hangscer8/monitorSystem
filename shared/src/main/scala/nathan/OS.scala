package nathan

import nathan.Protocols.top.mac.ProcessesInfo

import scala.sys.process._

trait OS

case object OSX extends OS

case object Linux extends OS

object OS {
  def whichOS: OS = {
    "uname".!!.trim match { // must "Darwin\n" trim去掉换行
      case "Darwin" => OSX
      case "Linux" => Linux
    }
  }
  def top(os:OS=OSX) = os match {
    case OSX => "top -l 1".!!.trim
    case Linux => "top -b -n 1".!!.trim
  }
  def jps(os:OS=OSX)= os match {
    case OSX => "jps".!!.trim
    case Linux => "jps".!!.trim
  }
}


// uname -a
//top -l 1
//top -b -n 1