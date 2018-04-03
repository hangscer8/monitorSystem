package nathan.util

import java.io.File

import com.typesafe.config.ConfigFactory
import org.hyperic.sigar.Sigar

import scala.io.Source

object SigarUtil {
  private val sigarHome = {
    val conf = if (new File(".", "application.conf").exists()) {
      ConfigFactory.parseFile(new File(".", "application.conf"))
    } else {
      ConfigFactory.parseString(Source.fromInputStream(this.getClass.getResourceAsStream("/application.conf")).mkString)
    }
    conf.getString("sigarDir")
  }

  System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + sigarHome)
  val sigarInstance = new Sigar()
}
