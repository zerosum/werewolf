package dev.zerosum.werewolf.actor

import akka.actor.testkit.typed.Effect.Spawned
import akka.actor.testkit.typed.scaladsl.BehaviorTestKit
import dev.zerosum.werewolf.system.Player
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should

class ModeratorSpec extends AnyFunSpec with should.Matchers {

  describe("Moderatorの振る舞い") {
    it("プレイヤーの参加と離脱") {
      val tested = BehaviorTestKit(Moderator("testVillage"))
      val player = Player("id1", "nick1")

      tested.run(Moderator.Join(player, "Annie"))
      tested.expectEffectType[Spawned[Participant.Command]]

      tested.run(Moderator.Leave(player))
      tested.childInbox[Participant.Command]("testVillage-id1").expectMessage(Participant.Leave)
    }
  }

}
