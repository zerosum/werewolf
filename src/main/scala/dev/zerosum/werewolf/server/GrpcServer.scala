package dev.zerosum.werewolf.server

import akka.actor.typed.ActorSystem
import akka.grpc.scaladsl.{ServerReflection, ServiceHandler}
import akka.http.scaladsl.Http
import dev.zerosum.werewolf.proto.{MatchingService, MatchingServiceHandler}

import scala.concurrent.{ExecutionContext, Future}

object GrpcServer {

  def init(
      matchingService: MatchingService
  )(implicit system: ActorSystem[_]): Future[Http.ServerBinding] = {
    implicit val ec: ExecutionContext = system.executionContext

    val serverReflection = ServerReflection.partial(
      List(
        MatchingService
      )
    )

    val serviceHandler = ServiceHandler.concatOrNotFound(
      serverReflection,
      MatchingServiceHandler.partial(matchingService)
    )

    val binding = Http()
      .newServerAt("localhost", -1)
      .bind(serviceHandler)

    binding.foreach { b =>
      system.log.debug("gRPC server bound to: {}", b.localAddress)
    }

    binding
  }
}
