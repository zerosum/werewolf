package dev.zerosum.werewolf.domain.ingame

case class Village(
    id: VillageId,
    casts: Casts,
    comments: Vector[Comment]
) {

  def start: Village = this.copy(casts = casts.assignRoles())

  def finish: Village = this.copy(casts = casts.copy(casts = casts.casts.map(_.copy(isAlive = true))))
}

object Village {

  def establish(villageId: VillageId): Village =
    Village(villageId, Casts.init(villageId), Vector.empty)
}
