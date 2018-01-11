package nathan

import java.io.File

import scala.io.Source

object Main extends App {
  val iterator = Source.fromFile(new File("/Users/jianghang/Desktop/top_ubuntu.txt")).getLines().map(_.trim)
  //  OS.top1(iterator.toList)
  val ExtractLoadAverage =
    """.*load\s+average[:]?\s+(\d*[.]?\d*)\s*[,]?\s*(\d*[.]?\d*)\s*[,]?\s*(\d*[.]?\d*)\s*""".r
  val ExtractLoadAverage(a,b,c)=iterator.toList.head
  println(a)
  println(b)
  println(c)
}

//jmap -histo:live 2131
// jmap -heap 2131