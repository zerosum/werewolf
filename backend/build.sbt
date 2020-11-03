name := "werewolf-backend"

scalaVersion := "2.13.3"

libraryDependencies ++= {
  val akkaVersion = "2.6.10"

  Seq(
    "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
    "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
    "org.scalactic"     %% "scalactic"                % "3.2.2"     % Test,
    "org.scalatest"     %% "scalatest"                % "3.2.2"     % Test
  )
}
