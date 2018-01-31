package nathan.sigarUtil

import java.io.File

import com.typesafe.config.ConfigFactory
import org.hyperic.sigar.Sigar

object SigarUtils {

  val sigar: Sigar = {
    //    val targetDirStr = System.getProperty("user.dir") + File.separator + "sigar"
    //    val targetDir = new File((targetDirStr))
    //    targetDir.mkdirs()
    //    List("libsigar-amd64-freebsd-6.so",
    //      "libsigar-amd64-linux.so",
    //      "libsigar-amd64-solaris.so",
    //      "libsigar-ia64-hpux-11.sl",
    //      "libsigar-ia64-linux.so",
    //      "libsigar-pa-hpux-11.sl",
    //      "libsigar-ppc-aix-5.so",
    //      "libsigar-ppc-linux.so",
    //      "libsigar-ppc64-aix-5.so",
    //      "libsigar-ppc64-linux.so",
    //      "libsigar-s390x-linux.so",
    //      "libsigar-sparc-solaris.so",
    //      "libsigar-sparc64-solaris.so",
    //      "libsigar-universal-macosx.dylib",
    //      "libsigar-universal64-macosx.dylib",
    //      "libsigar-x86-freebsd-5.so",
    //      "libsigar-x86-freebsd-6.so",
    //      "libsigar-x86-linux.so",
    //      "libsigar-x86-solaris.so",
    //      "sigar-amd64-winnt.dll",
    //      "sigar-x86-winnt.dll",
    //      "sigar-x86-winnt.lib").foreach { path =>
    //      val buffer = new Array[Byte](1024)
    //      val inputStream = this.getClass.getResourceAsStream(s"/sigar/$path")
    //      val outputFile = new File(targetDirStr + File.separator + path)
    //      val outputStream = new FileOutputStream(outputFile)
    //      while (inputStream.read(buffer) != -1) {
    //        outputStream.write(buffer)
    //      }
    //      outputStream.flush() //必须存在
    //      inputStream.close()
    //      outputStream.close()
    //    }
    val sigarDir = ConfigFactory.load().getString("sigarDir")
    System.getProperty("java.library.path").contains("sigar") match {
      case true =>
      case false =>
        System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + sigarDir)
    }
    new Sigar()
  }

  def isWinos() = System.getProperty("os.name").toLowerCase.contains("win")
}