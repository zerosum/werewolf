package dev.zerosum.werewolf.server

import dev.zerosum.werewolf.proto.{EstablishRequest, EstablishResponse, MatchingService}

import scala.concurrent.Future

class MatchingServiceImpl extends MatchingService {

  override def establish(in: EstablishRequest): Future[EstablishResponse] = {
    Future.successful(EstablishResponse(id = "hoge"))
  }
}
