package nathan.util

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import nathan.entity._
import nathan.monitorSystem.Protocols._
import org.scalajs.dom.raw._

object implicitDecoder {
  implicit val retSuccessDecoder: Decoder[RetMsg] = deriveDecoder[RetMsg]
  implicit val cpuPercEntityDecoder: Decoder[CPUPercEntity] = deriveDecoder[CPUPercEntity]

  implicit class NodeListOps(nodeList: NodeList) {
    def map[T](apply: Node => T): Seq[T] = {
      val length = nodeList.length
      (1 to length).map(i => apply(nodeList(i)))
    }

    def foreach(apply: Node => Unit): Unit = {
      val length = nodeList.length
      (1 to length).foreach(i => apply(nodeList(i)))
    }

    def filter(apply: Node => Boolean): Seq[Node] = {
      map(i => (i, apply(i))).filter(_._2 == true).map(_._1)
    }
  }

}