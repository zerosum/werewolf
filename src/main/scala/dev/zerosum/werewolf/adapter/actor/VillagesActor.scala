package dev.zerosum.werewolf.adapter.actor

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object VillagesActor {
  sealed trait Command
  case class Establish(replyTo: ActorRef[Reply]) extends Command

  sealed trait Reply
  case class EstablishSucceeded(villageId: String) extends Reply

  def apply(): Behavior[Command] = Behaviors.setup { ctx =>
    Behaviors.receiveMessagePartial { case Establish(replyTo) =>
      ctx.spawn(VillageActor.establish(), "TODO")
      replyTo ! EstablishSucceeded("TODO")
      Behaviors.same
    }

  }
}
