
name := "shared"

version := "0.1"

scalaVersion := "2.12.3"
libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor_2.12" % "2.4.19"
)

lazy val commonSettings = Seq(
  version := "0.1",
  organization := "hangscer.win",
  scalaVersion := "2.12.3",
  test in assembly := {}
)

lazy val app = (project in file("shared")).
  settings(commonSettings: _*).
  settings(
    mainClass in assembly := Some("nathan.Main"),
    assemblyJarName in assembly := "shared.jar"
  )
cancelable in Global := true