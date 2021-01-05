package dev.zerosum.werewolf.actor

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import dev.zerosum.werewolf.system.Player

object Moderator {

  sealed trait Command
  case class Join(player: Player, name: String) extends Command
  case class Leave(player: Player)              extends Command
  case object Start                             extends Command
  case object Sunset                            extends Command
  case object Dawn                              extends Command

  def apply(villageName: String): Behavior[Command] = Behaviors.setup { ctx =>
    new Moderator(villageName, ctx).init()
  }
}

class Moderator private (
    villageName: String,
    ctx: ActorContext[Moderator.Command]
) {
  import Moderator._

  private def init(): Behavior[Command] =
    Behaviors.receiveMessagePartial {
      case Join(player, name) =>
        ctx.spawn(Participant(player, name), participantName(player))
        Behaviors.same

      case Leave(player: Player) =>
        ctx
          .child(participantName(player))
          .foreach { case participant: ActorRef[Participant.Command] =>
            participant ! Participant.Leave
          }
        Behaviors.same

      case Start =>
        becomeDaytime(1)
    }

  private[this] def becomeDaytime(day: Int): Behavior[Command] =
    Behaviors.receiveMessagePartial { case Sunset =>
      becomeNight(day)
    }

  private[this] def becomeNight(day: Int): Behavior[Command] =
    Behaviors.receiveMessagePartial { case Dawn =>
      becomeDaytime(day + 1)
    }

  private[this] def participantName(player: Player): String = s"$villageName-${player.id}"
}
