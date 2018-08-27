package benoit.invites

import benoit.invites.InviteService.{ErrorInfo, States}
import cats.data.EitherT

import scala.concurrent.Future

object InviteService {

  sealed trait States
  case object StartingEvaluation extends States
  case object NotEnrolled extends States
  case object InvitedFirstTime extends States
  case object Invited extends States
  case object Enrolled extends States

  case class ErrorInfo()
}

trait InviteService {

  type ErrorOrUserState = EitherT[Future, ErrorInfo, States]

  def evaluateStateForUser(serviceResponses: ServiceResponses): ErrorOrUserState
}

trait ServiceResponses {
  def userIsAlreadyEnrolled:Boolean
  def userIsEligible:Boolean
  def userIsAlreadyInvited:Boolean
  def isOverDailyInviteLimit:Boolean
}