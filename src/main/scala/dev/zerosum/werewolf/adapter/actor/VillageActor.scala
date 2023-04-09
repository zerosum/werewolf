package dev.zerosum.werewolf.adapter.actor

import akka.actor.typed._
import akka.actor.typed.scaladsl.Behaviors
import dev.zerosum.werewolf.domain.Player
import dev.zerosum.werewolf.domain.ingame.{Cast, Comment, Role, Village, VillageId}

object VillageActor {
  sealed trait Command
  case object Start   extends Command
  case object Dawn    extends Command
  case object Sunset  extends Command
  case object Finish  extends Command
  case object Archive extends Command

  case class DoComment(player: Player, `type`: Comment.Type, text: String) extends Command {
    def isValid(village: Village): Boolean = {
      village.casts.of(player).exists {
        case Cast(_, _, _, _, true) if `type` == Comment.Public                                 => true
        case Cast(_, _, Role.Werewolf, _, _) if `type` == Comment.Whisper                       => true
        case Cast(_, _, role, _, false) if role != Role.Werewolf && `type` == Comment.Graveyard => true
        case _ if `type` == Comment.Private                                                     => true
        case _                                                                                  => false
      }
    }
  }

  def apply(villageId: VillageId): Behavior[Command] =
    established(Village.establish(villageId))

  private def established(village: Village): Behavior[Command] =
    Behaviors.receiveMessagePartial {
      case Start =>
        daytime(village.start)
      case cmd @ DoComment(_, Comment.Public | Comment.Private, _) if cmd.isValid(village) =>
        doComment(cmd, village, established)
      case _ =>
        Behaviors.same
    }

  private def daytime(village: Village): Behavior[Command] = Behaviors.setup { ctx =>
    Behaviors.receiveMessagePartial {
      case cmd: DoComment if cmd.isValid(village) =>
        doComment(cmd, village, daytime)
      case Sunset =>
        night(village)
      case _ =>
        Behaviors.same
    }
  }

  private def night(village: Village): Behavior[Command] = Behaviors.setup { ctx =>
    Behaviors.receiveMessagePartial {
      case cmd: DoComment if cmd.`type` != Comment.Public && cmd.isValid(village) =>
        doComment(cmd, village, night)
      case Dawn =>
        daytime(village)
      case Finish =>
        finished(village.finish)
      case _ =>
        Behaviors.same
    }
  }

  private def finished(village: Village): Behavior[Command] = Behaviors.setup { ctx =>
    Behaviors.receiveMessagePartial {
      case cmd @ DoComment(_, Comment.Public, _) if cmd.isValid(village) =>
        doComment(cmd, village, finished)
      case Archive =>
        archived(village)
      case _ =>
        Behaviors.same
    }
  }

  def archived(village: Village): Behavior[Command] = Behaviors.setup { ctx =>
    Behaviors.receiveMessagePartial { case _ => Behaviors.same }
  }

  private def doComment(cmd: DoComment, village: Village, f: Village => Behavior[Command]): Behavior[Command] = {
    val whose = village.casts.of(cmd.player).get.character
    f(
      village.copy(
        comments = village.comments :+ Comment.create(village.id, whose, cmd.text, cmd.`type`)
      )
    )
  }
}
