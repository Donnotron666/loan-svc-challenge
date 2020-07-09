name := "aria-loan-service-challenge"

version := "0.1"

scalaVersion := "2.11.8"

mainClass in (Compile, run) := Some("Main")

cancelable in Global := true

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.25",
  "com.typesafe.akka" %% "akka-stream" % "2.5.25",
  "com.typesafe.akka" %% "akka-http" % "10.1.8",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8",
  "joda-time" % "joda-time" % "2.10.6"
)

