package dev.zerosum.werewolf.domain.ingame

sealed trait Role

object Role {
  sealed trait Side
  trait Villagers  extends Side
  trait Werewolves extends Side

  case object Candidate extends Role
  case object Villager  extends Role with Villagers
  case object Werewolf  extends Role with Werewolves
  case object Seer      extends Role with Villagers
  case object Medium    extends Role with Villagers
  case object Bodyguard extends Role with Villagers
  case object Possessed extends Role with Werewolves
  case object Freemason extends Role with Villagers
}
