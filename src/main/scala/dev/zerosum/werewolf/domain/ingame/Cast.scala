package dev.zerosum.werewolf.domain.ingame

import dev.zerosum.werewolf.domain.Player

import scala.util.Random

case class Cast(villageId: VillageId, character: Character, role: Role, player: Player, isAlive: Boolean)

case class Casts private (
    villageId: VillageId,
    casts: Vector[Cast],
    private val unassignedCharacters: Vector[Character]
) {

  def join(player: Player): Casts = {
    require(unassignedCharacters.nonEmpty)
    require(casts.length <= 13)

    val random   = new Random()
    val shuffled = random.shuffle(unassignedCharacters)

    this.copy(
      casts = Cast(villageId, shuffled.head, Role.Candidate, player, isAlive = true) +: casts,
      unassignedCharacters = shuffled.tail
    )
  }

  def assignRoles(): Casts = {
    val numOfCasts = casts.length
    require(4 <= numOfCasts && numOfCasts <= 13)

    val random              = new Random()
    val roles: Vector[Role] = Casts.roles(numOfCasts)
    this.copy(
      casts = casts.zip(random.shuffle(roles)).map { case (cast, role) => cast.copy(role = role) },
      unassignedCharacters = Vector.empty
    )
  }

  def of(player: Player): Option[Cast] = casts.find(_.player == player)
}

object Casts {
  def init(villageId: VillageId): Casts = new Casts(villageId, Vector.empty, Characters)

  private final val Characters: Vector[Character] = Vector(
    Character("Alice"),
    Character("Bob"),
    Character("Carol"),
    Character("David"),
    Character("Eve"),
    Character("Franc"),
    Character("Grace"),
    Character("Heidi"),
    Character("Ivan"),
    Character("Judy"),
    Character("Mallory"),
    Character("Niaj"),
    Character("Olivia"),
    Character("Peggy"),
    Character("Rupert"),
    Character("Sybil"),
    Character("Trent"),
    Character("Victor"),
    Character("Walter")
  )

  private def roles(numOfCasts: Int): Vector[Role] = {
    val abilityRoles = numOfCasts match {
      case 4 | 5 | 6 | 7 => Vector(Role.Seer, Role.Werewolf)
      case 8             => Vector(Role.Seer, Role.Werewolf, Role.Werewolf)
      case 9             => Vector(Role.Seer, Role.Medium, Role.Werewolf, Role.Werewolf)
      case 10            => Vector(Role.Seer, Role.Medium, Role.Werewolf, Role.Werewolf, Role.Possessed)
      case 11 | 12       => Vector(Role.Seer, Role.Medium, Role.Bodyguard, Role.Werewolf, Role.Werewolf, Role.Possessed)
      case 13 =>
        Vector(
          Role.Seer,
          Role.Medium,
          Role.Bodyguard,
          Role.Freemason,
          Role.Freemason,
          Role.Werewolf,
          Role.Werewolf,
          Role.Possessed
        )
    }

    abilityRoles ++ Vector.fill(numOfCasts - abilityRoles.length)(Role.Villager)
  }
}
