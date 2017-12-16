
name := "agent"

version := "0.1"

scalaVersion := "2.12.3"
libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor_2.12" % "2.4.19",
  "com.typesafe.akka" % "akka-stream_2.12" % "2.4.19",
  "com.typesafe.akka" % "akka-http_2.12" % "10.0.10",
  "com.typesafe.akka" % "akka-cluster_2.12" % "2.4.19",
  "com.typesafe.akka" % "akka-persistence_2.12" % "2.4.19",
  "com.typesafe.akka" %% "akka-remote" % "2.4.19",
  "com.zaxxer" % "HikariCP" % "2.6.3",
  "mysql" % "mysql-connector-java" % "5.1.23",
  "com.typesafe.slick" % "slick_2.12" % "3.2.0",
  "io.circe" %% "circe-core" % "0.9.0-M2",
  "io.circe" %% "circe-generic" % "0.9.0-M2",
  "io.circe" %% "circe-parser" % "0.9.0-M2",
  "junit" % "junit" % "4.12",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.2",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.2",
  "org.ensime" %% "api" % "2.0.0",
  "org.typelevel" %% "cats-core" % "1.0.0-RC1"
)
cancelable in Global := true

lazy val commonSettings = Seq(
  version := "0.1",
  organization := "hangscer.win",
  scalaVersion := "2.12.3",
  test in assembly := {}
)

lazy val app = (project in file("agent")).
  settings(commonSettings: _*).
  settings(
    mainClass in assembly := Some("nathan.Main"),
    assemblyJarName in assembly := "agent.jar"
  )
cancelable in Global := true