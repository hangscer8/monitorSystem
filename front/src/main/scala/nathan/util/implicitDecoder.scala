package nathan.util

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import nathan.entity._
import nathan.monitorSystem.Protocols._

object implicitDecoder {
  implicit val retSuccessDecoder: Decoder[RetMsg] = deriveDecoder[RetMsg]
  implicit val cpuPercEntityDecoder: Decoder[CPUPercEntity] = deriveDecoder[CPUPercEntity]
}
