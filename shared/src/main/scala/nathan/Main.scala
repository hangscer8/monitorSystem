package nathan

object Main extends App {
  val list = OS.`jmap -heap pid`(7434)
  println(list)
}

//jmap -histo:live 2131
// jmap -heap 2131