package util

import io.circe.generic.auto._
import io.circe.syntax._
import json.Protocol._
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData
import service.Protocol._

trait LoginSupport {
  def setAuth(authOption: Option[String] = None) = authOption match {
    case _: Some[_] =>
      window.localStorage.setItem(authHead, authOption.get)
    case None =>
      window.localStorage.setItem(authHead, DefaultAuth.authValue)
  }
  def login(user:UserEntity)={
    Ajax.post(url=baseUrl/"monitorSystem"/"login",data = InputData.str2ajax(user.asJson.spaces2))
  }
}