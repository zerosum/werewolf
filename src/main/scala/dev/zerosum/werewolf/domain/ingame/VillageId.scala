package dev.zerosum.werewolf.domain.ingame

import java.util.UUID

case class VillageId private (value: String)

object VillageId {
  def generate: VillageId =
    new VillageId(UUID.randomUUID().toString.replaceAll("-", ""))
}
