package dev.zerosum.werewolf.adapter.actor

import akka.actor.typed._
import akka.actor.typed.scaladsl.Behaviors

object VillageActor {
  sealed trait Command

  def establish(): Behavior[Command] = Behaviors.setup { ctx =>
    ctx.log.debug("established")

    Behaviors.receiveMessagePartial { case _ =>
      Behaviors.same
    }
  }
}
