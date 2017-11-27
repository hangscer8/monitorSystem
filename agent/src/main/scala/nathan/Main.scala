package nathan
import nathan.ExtractUtils._

import scala.sys.process._
import scala.util.{Failure, Success, Try}
object Main extends App{
  val str=OS.jps()
}
//jmap -histo:live 2131
// jmap -heap 2131