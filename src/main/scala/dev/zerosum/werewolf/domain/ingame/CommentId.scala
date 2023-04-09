package dev.zerosum.werewolf.domain.ingame

import java.util.UUID

case class CommentId private (value: String)

object CommentId {
  def generate: CommentId =
    new CommentId(UUID.randomUUID().toString.replaceAll("-", ""))
}
