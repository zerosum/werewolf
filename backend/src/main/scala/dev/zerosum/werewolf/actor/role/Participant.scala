package dev.zerosum.werewolf.actor.role

trait Participant {

  sealed trait Command
  final case object Leave                extends Command
  final case class Chat(message: String) extends Command
  final case class Vote(target: String)  extends Command
}
