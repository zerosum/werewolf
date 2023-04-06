package dev.zerosum.werewolf

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.typesafe.config.ConfigFactory
import dev.zerosum.werewolf.server.GrpcServer

object Main {

  def main(args: Array[String]): Unit = {
    val conf   = ConfigFactory.load()
    val system = ActorSystem(Behaviors.empty[String], "werewolf", conf)

    new GrpcServer(system).run()
  }
}
