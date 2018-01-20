package nathan.sigarUtil

import java.nio.file.Paths

import org.hyperic.sigar.Sigar

object SigarUtils {

  val sigar: Sigar = {
    val path = Paths.get(this.getClass.getResource("/").getPath)
    val sigarLibDir = path.resolve("sigar")
    System.setProperty("java.library.path", System.getProperty("java.library.path") + (if (isWinos()) ";" else ":") + sigarLibDir.toString)
    new Sigar()
  }

  private[this] def isWinos() = System.getProperty("os.name").toLowerCase.contains("win")
}