name := "akka-http-quick"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.2",
  "com.typesafe.akka" %% "akka-http-experimental" % "1.0",
  "com.typesafe.akka" %% "akka-http-xml-experimental" % "1.0",
  "org.json4s" %% "json4s-jackson" % "3.2.11")
