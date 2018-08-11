package controllers.register

import java.io.File
import javax.inject._

import conf.PlayConf
import io.circe.Printer
import io.circe.generic.auto._
import io.circe.syntax._
import nathan.monitorSystem.Protocols.UserEntity
import play.api.libs.circe.Circe
import play.api.mvc._
import service.register.RegisterServiceTrait
import util.{UtilTrait}
import entity.EntityTable._
import h2.api._
import util.ExecutorService._
import scala.util.{Failure, Success}

@Singleton
class RegisterController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with RegisterServiceTrait with UtilTrait {
  implicit val codec = Codec.utf_8
  implicit val customPrinter = Printer.spaces2.copy(dropNullValues = false)

  def index = Action { request =>
    Ok(views.html.register.index("注册页面"))
  }

  def registerUser() = Action(circe.json[UserEntity]) { request => //json反序列化
    db.run(registerUserDBIO(request.body).transactionally.asTry).map {
      case Success(userEntity) =>
        Ok(Map("code" -> "0000", "entityJson" -> userEntity.asJson.noSpaces).asJson.noSpaces).as(JSON).withSession((loginKey, "true"))
      case Failure(ex) =>
        Ok(Map("code" -> "0001", "errorMsg" -> s"${ex}").asJson.noSpaces).as(JSON).withNewSession
    }.exe
  }

  def upload() = Action(parse.multipartFormData) { request =>
    request.body.file("file").map { file =>
      val newName = reNameFile(file.filename)
      (newName, file.ref.moveTo(new File(PlayConf.uploadDir, newName), true))
    } match {
      case None =>
        Ok(Map("code" -> "0001", "message" -> "没有上传文件!!").asJson.noSpaces).as(JSON)
      case Some((newFileName, _)) =>
        Ok(Map("code" -> "0000", "newFileName" -> newFileName).asJson.noSpaces).as(JSON) //json序列化
    }
  }
}