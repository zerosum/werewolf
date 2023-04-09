package dev.zerosum.werewolf.adapter.actor

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import dev.zerosum.werewolf.domain.ingame.VillageId

object VillagesActor {
  sealed trait Command
  case class Establish(replyTo: ActorRef[Reply]) extends Command

  sealed trait Reply
  case class EstablishSucceeded(villageId: VillageId) extends Reply

  def apply(): Behavior[Command] = Behaviors.setup { ctx =>
    Behaviors.receiveMessagePartial { case Establish(replyTo) =>
      val villageId = VillageId.generate
      ctx.spawn(VillageActor(villageId), villageId.value)
      replyTo ! EstablishSucceeded(villageId)
      Behaviors.same
    }

  }
}
