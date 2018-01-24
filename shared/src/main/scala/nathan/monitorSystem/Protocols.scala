package nathan.monitorSystem


package Protocols {

  case class CPUPerc(user: Double, sys: Double, _wait: Double, idle: Double, combined: Double) //user用户使用率 sys系统使用率 _wait当前等待率 idle空闲率 combined 总的使用率
  case class ME()
}