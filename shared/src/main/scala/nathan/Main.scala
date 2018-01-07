package nathan

object Main extends App {
  OS.`jmap -heap pid`(4886).foreach(println)
}

//jmap -histo:live 2131
// jmap -heap 2131