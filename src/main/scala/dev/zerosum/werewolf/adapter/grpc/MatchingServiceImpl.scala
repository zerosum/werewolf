package dev.zerosum.werewolf.adapter.grpc

import akka.actor.typed.scaladsl.AskPattern.Askable
import akka.actor.typed.{ActorRef, Scheduler}
import akka.util.Timeout
import dev.zerosum.werewolf.adapter.actor.VillagesActor
import dev.zerosum.werewolf.proto.{EstablishRequest, EstablishResponse, MatchingService}

import scala.concurrent.{ExecutionContext, Future}

class MatchingServiceImpl(
    villagesActorRef: ActorRef[VillagesActor.Command]
)(implicit val timeout: Timeout, ec: ExecutionContext, scheduler: Scheduler)
    extends MatchingService {

  override def establish(in: EstablishRequest): Future[EstablishResponse] = {
    for {
      res <- villagesActorRef.ask[VillagesActor.Reply] { replyTo =>
        VillagesActor.Establish(replyTo)
      }
    } yield res match {
      case r: VillagesActor.EstablishSucceeded =>
        EstablishResponse(r.villageId.value)
    }
  }
}
