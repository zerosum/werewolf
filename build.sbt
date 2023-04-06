import Dependencies.*

enablePlugins(AkkaGrpcPlugin)

organization := "dev.zerosum"

libraryDependencies ++= Seq(
  akka.actor,
  akka.cluster,
  akka.stream,
  akka.http,
  scalapbRuntime
)

mainClass := Some("dev.zerosum.werewolf.Main")
