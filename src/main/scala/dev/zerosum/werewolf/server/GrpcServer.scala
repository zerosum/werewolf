package dev.zerosum.werewolf.server

import akka.actor.typed.ActorSystem
import akka.grpc.scaladsl.{ServerReflection, ServiceHandler}
import akka.http.scaladsl.Http
import dev.zerosum.werewolf.proto.{MatchingService, MatchingServiceHandler}

import scala.concurrent.{ExecutionContext, Future}

class GrpcServer(system: ActorSystem[_]) {

  def run(): Future[Http.ServerBinding] = {
    implicit val sys: ActorSystem[_]  = system
    implicit val ec: ExecutionContext = sys.executionContext

    val matchingService = MatchingServiceHandler.partial(new MatchingServiceImpl())

    val serverReflection = ServerReflection.partial(
      List(
        MatchingService
      )
    )

    val serviceHandler = ServiceHandler.concatOrNotFound(
      serverReflection,
      matchingService
    )

    val binding = Http()
      .newServerAt("localhost", -1)
      .bind(serviceHandler)

    binding.foreach { b =>
      println(s"gRPC server bound to: ${b.localAddress}")
    }

    binding
  }
}
