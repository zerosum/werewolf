package dev.zerosum.werewolf.actor

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import dev.zerosum.werewolf.system.Player

object Participant extends role.Participant {

  def apply(player: Player, name: String): Behavior[Command] =
    Behaviors.receiveMessagePartial { case Leave =>
      Behaviors.stopped
    }

}
