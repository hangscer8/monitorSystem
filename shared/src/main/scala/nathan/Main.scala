package nathan
import nathan.ec.ExecutorServiceUtil.{agentActor}
object Main extends App{
  println(agentActor)
}
//jmap -histo:live 2131
// jmap -heap 2131