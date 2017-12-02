package util

import entity.{UserReq}
import io.circe.generic.auto._
import io.circe.syntax._
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData
import util.CommonUtil._
import util.CommonConst._
trait LoginSupport extends HttpHeadSupport{
  def setAuth(authOption: Option[String] = None) = authOption match {
    case _: Some[_] =>
      window.localStorage.setItem(authHead, authOption.get)
    case None =>
      window.localStorage.setItem(authHead, DefaultAuth.authValue)
  }
  def login(user:UserReq)={
    Ajax.post(url=baseUrl/"monitorSystem"/"login",data = InputData.str2ajax(user.asJson.spaces2),headers = header)
  }
}