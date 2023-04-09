import Dependencies.*

enablePlugins(AkkaGrpcPlugin)

organization := "dev.zerosum"

libraryDependencies ++= Seq(
  akka.actor,
  akka.cluster,
  akka.stream,
  akka.http,
  akka.slf4j,
  scalapbRuntime,
  logback,
  scalatest
)

mainClass := Some("dev.zerosum.werewolf.Main")
