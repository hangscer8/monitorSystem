//import play.sbt.PlayScala
//name := "centerRouter"
//
//version := "0.1"
//
////scalaHome := Some(file("/usr/local/Cellar/scala"))
//scalaVersion := "2.12.3"
//libraryDependencies ++= Seq(
//  "com.typesafe.akka" % "akka-actor_2.12" % "2.4.19",
//  "com.typesafe.akka" % "akka-stream_2.12" % "2.4.19",
//  "com.typesafe.akka" % "akka-http_2.12" % "10.0.10",
//  "com.typesafe.akka" % "akka-cluster_2.12" % "2.4.19",
//  "com.typesafe.akka" % "akka-persistence_2.12" % "2.4.19",
//  "com.typesafe.akka" %% "akka-remote" % "2.4.19",
//  "mysql" % "mysql-connector-java" % "5.1.23",
//  "com.typesafe.slick" %% "slick" % "3.2.0",
//  "org.slf4j" % "slf4j-nop" % "1.6.4",
//  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
//  "io.circe" %% "circe-core" % "0.9.0-M2",
//  "io.circe" %% "circe-generic" % "0.9.0-M2",
//  "io.circe" %% "circe-parser" % "0.9.0-M2",
//  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.10",
//  "junit" % "junit" % "4.12",
//  "com.typesafe.slick" %% "slick-codegen" % "3.2.0",
//  "com.h2database" % "h2" % "1.4.186",
//  "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.2",
//  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.2",
//  "com.typesafe.akka" %% "akka-http-jackson" % "10.0.10",
//  "org.ensime" %% "api" % "2.0.0",
//  "org.typelevel" %% "cats-core" % "1.0.0-RC1"
//)
//lazy val commonSettings = Seq(
//  version := "0.1",
//  organization := "hangscer.win",
//  scalaVersion := "2.12.3"
//)
//lazy val app = (project in file("centerRouter"))
//cancelable in Global := true
//enablePlugins(PlayScala)