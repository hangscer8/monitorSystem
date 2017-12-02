package util

import service.Protocol.{DefaultAuth, authHead}

trait HttpHeadSupport {
  val `Content-Type` = "Content-Type"
  val `"application/json` = "application/json"
  val header = Map("Content-Type" -> "application/json", authHead -> DefaultAuth.authValue)
}