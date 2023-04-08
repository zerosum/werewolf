import sbt.*

object Dependencies {

  final val AkkaVersion     = "2.7.0"
  final val AkkaHttpVersion = "10.5.0"

  object akka {
    val actor   = "com.typesafe.akka" %% "akka-actor-typed"   % AkkaVersion
    val cluster = "com.typesafe.akka" %% "akka-cluster-typed" % AkkaVersion
    val stream  = "com.typesafe.akka" %% "akka-stream"        % AkkaVersion
    val http    = "com.typesafe.akka" %% "akka-http"          % AkkaHttpVersion
    val slf4j   = "com.typesafe.akka" %% "akka-slf4j"         % AkkaVersion
  }

  val scalapbRuntime =
    "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"

  val logback = "ch.qos.logback" % "logback-classic" % "1.2.11"
}
