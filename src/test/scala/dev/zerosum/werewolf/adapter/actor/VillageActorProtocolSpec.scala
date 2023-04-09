package dev.zerosum.werewolf.adapter.actor

import dev.zerosum.werewolf.adapter.actor.VillageActor.DoComment
import dev.zerosum.werewolf.domain.Player
import dev.zerosum.werewolf.domain.ingame._
import org.scalatest.freespec.AnyFreeSpec

class VillageActorProtocolSpec extends AnyFreeSpec {
  import VillageActorProtocolSpec._

  "DoComment" - {
    "isValid" - {
      "`Public` should be `true` for only alive characters." in {
        val actualAliveVillager = DoComment(`given`.player1, Comment.Public, "__text__").isValid(`given`.village)
        val actualDeadVillager  = DoComment(`given`.player2, Comment.Public, "__text__").isValid(`given`.village)
        val actualAliveWerewolf = DoComment(`given`.player3, Comment.Public, "__text__").isValid(`given`.village)
        val actualDeadWerewolf  = DoComment(`given`.player4, Comment.Public, "__text__").isValid(`given`.village)

        assert(actualAliveVillager, "An alive villager is able to comment to Public.")
        assert(!actualDeadVillager, "A dead villager isn't able to comment to Public.")
        assert(actualAliveWerewolf, "An alive werewolf is able to comment to Public.")
        assert(!actualDeadWerewolf, "A dead werewolf isn't able to comment to Public.")
      }
      "`Private` should be `true` for any characters." in {
        val actualAliveVillager = DoComment(`given`.player1, Comment.Private, "__text__").isValid(`given`.village)
        val actualDeadVillager  = DoComment(`given`.player2, Comment.Private, "__text__").isValid(`given`.village)
        val actualAliveWerewolf = DoComment(`given`.player3, Comment.Private, "__text__").isValid(`given`.village)
        val actualDeadWerewolf  = DoComment(`given`.player4, Comment.Private, "__text__").isValid(`given`.village)

        assert(actualAliveVillager, "An alive villager is able to comment to Private.")
        assert(actualDeadVillager, "A dead villager is able to comment to Private.")
        assert(actualAliveWerewolf, "An alive werewolf is able to comment to Private.")
        assert(actualDeadWerewolf, "A dead werewolf is able to comment to Private.")
      }
      "`Whisper` should be `true` for only werewolves." in {
        val actualAliveVillager = DoComment(`given`.player1, Comment.Whisper, "__text__").isValid(`given`.village)
        val actualDeadVillager  = DoComment(`given`.player2, Comment.Whisper, "__text__").isValid(`given`.village)
        val actualAliveWerewolf = DoComment(`given`.player3, Comment.Whisper, "__text__").isValid(`given`.village)
        val actualDeadWerewolf  = DoComment(`given`.player4, Comment.Whisper, "__text__").isValid(`given`.village)

        assert(!actualAliveVillager, "An alive villager isn't able to comment to Whisper.")
        assert(!actualDeadVillager, "A dead villager isn't able to comment to Whisper.")
        assert(actualAliveWerewolf, "An alive werewolf is able to comment to Whisper.")
        assert(actualDeadWerewolf, "A dead werewolf is able to comment to Whisper.")
      }
      "`Graveyard` should be `true` for only dead villagers." in {
        val actualAliveVillager = DoComment(`given`.player1, Comment.Graveyard, "__text__").isValid(`given`.village)
        val actualDeadVillager  = DoComment(`given`.player2, Comment.Graveyard, "__text__").isValid(`given`.village)
        val actualAliveWerewolf = DoComment(`given`.player3, Comment.Graveyard, "__text__").isValid(`given`.village)
        val actualDeadWerewolf  = DoComment(`given`.player4, Comment.Graveyard, "__text__").isValid(`given`.village)

        assert(!actualAliveVillager, "An alive villager isn't able to comment to Whisper.")
        assert(actualDeadVillager, "A dead villager is able to comment to Whisper.")
        assert(!actualAliveWerewolf, "An alive werewolf isn't able to comment to Whisper.")
        assert(!actualDeadWerewolf, "A dead werewolf isn't able to comment to Whisper.")
      }
    }
  }
}

object VillageActorProtocolSpec {
  private object `given` {
    val villageId: VillageId = VillageId.generate

    val player1: Player = Player("__id1__", "Alice")
    val player2: Player = Player("__id2__", "Bob")
    val player3: Player = Player("__id3__", "Carol")
    val player4: Player = Player("__id4__", "David")

    val aliveVillager: Cast = Cast(
      villageId = villageId,
      character = Character("Alice"),
      role = Role.Villager,
      player = player1,
      isAlive = true
    )
    val deadVillager: Cast = Cast(
      villageId = villageId,
      character = Character("Bob"),
      role = Role.Villager,
      player = player2,
      isAlive = false
    )
    val aliveWerewolf: Cast = Cast(
      villageId = villageId,
      character = Character("Carol"),
      role = Role.Werewolf,
      player = player3,
      isAlive = true
    )
    val deadWerewolf: Cast = Cast(
      villageId = villageId,
      character = Character("David"),
      role = Role.Werewolf,
      player = player4,
      isAlive = false
    )

    val village = Village(
      id = villageId,
      casts = Casts(
        villageId = villageId,
        casts = Vector(aliveVillager, deadVillager, aliveWerewolf, deadWerewolf),
        unassignedCharacters = Vector.empty
      ),
      comments = Vector.empty
    )

  }
}
