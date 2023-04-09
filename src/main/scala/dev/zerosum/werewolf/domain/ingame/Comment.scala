package dev.zerosum.werewolf.domain.ingame

import java.time.Instant

case class Comment(
    id: CommentId,
    villageId: VillageId,
    whose: Character,
    text: String,
    `type`: Comment.Type,
    commentedAt: Instant
)

object Comment {
  sealed trait Type
  case object Public    extends Type
  case object Private   extends Type
  case object Whisper   extends Type
  case object Graveyard extends Type

  def create(villageId: VillageId, whose: Character, text: String, `type`: Type): Comment =
    new Comment(CommentId.generate, villageId, whose, text, `type`, Instant.now())
}
