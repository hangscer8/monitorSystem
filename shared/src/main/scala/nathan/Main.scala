package nathan

object Main extends App {
  val jheap = OS.`jmap -heap pid`(9722)
  println(jheap)
}

//jmap -histo:live 2131
// jmap -heap 2131