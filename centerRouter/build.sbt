name := "centerRouter"

version := "0.1"

//scalaHome := Some(file("/usr/local/Cellar/scala"))
scalaVersion := "2.12.4"
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
  "io.circe" %% "circe-core" % "0.8.0",
  "io.circe" %% "circe-generic" % "0.8.0",
  "io.circe" %% "circe-parser" % "0.8.0",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.10",
  "junit" % "junit" % "4.12",
  "com.typesafe.slick" %% "slick-codegen" % "3.2.0"
)