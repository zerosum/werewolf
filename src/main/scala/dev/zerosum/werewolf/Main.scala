package dev.zerosum.werewolf

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior, Scheduler}
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import dev.zerosum.werewolf.adapter.actor.VillagesActor
import dev.zerosum.werewolf.adapter.grpc.MatchingServiceImpl
import dev.zerosum.werewolf.server.GrpcServer

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

object Main {

  def main(args: Array[String]): Unit = {
    ActorSystem(guardian(), "werewolf", ConfigFactory.load())
  }

  private def guardian(): Behavior[Any] = {
    Behaviors.setup { ctx =>
      implicit val system: ActorSystem[_] = ctx.system
      implicit val ec: ExecutionContext   = system.executionContext
      implicit val timeout: Timeout       = 3.second
      implicit val scheduler: Scheduler   = system.scheduler

      val villagesActorRef    = ctx.spawn(VillagesActor(), "villages")
      val matchingServiceImpl = new MatchingServiceImpl(villagesActorRef)

      GrpcServer.init(matchingServiceImpl)

      Behaviors.same
    }
  }
}
