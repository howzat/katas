package benoit.cats

import benoit.cats.InviteService._
import cats.data.EitherT
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.Future._

class BasicInviteService extends InviteService {

  private sealed trait FlowControl
  private case class Complete(state: States) extends FlowControl
  private case object Continue extends FlowControl

  private type NextStep = EitherT[Future, ErrorInfo, FlowControl]


  private def callAnotherMicroserviceThatReturns(returns: Boolean): Future[Either[ErrorInfo, Boolean]] = {
    Future(Right(returns))
  }

  private def continueRunningSteps: NextStep = {
    EitherT.right[ErrorInfo](Future(Continue))
  }

  private def completeWithUserState(state: States): NextStep = {
    EitherT.right[ErrorInfo](Future(Complete(state)))
  }

  private def isAlreadyEnrolled(state: Boolean): NextStep = {
    EitherT(callAnotherMicroserviceThatReturns(state)).flatMap {
      case isEnrolled if isEnrolled => completeWithUserState(Enrolled)
      case _ => continueRunningSteps
    }
  }

  private def isUserEligibleToBeInvited(state: Boolean): NextStep = {
    EitherT(callAnotherMicroserviceThatReturns(state)).flatMap {
      case isEligible if isEligible => continueRunningSteps
      case _ => completeWithUserState(NotEnrolled)
    }
  }

  private def isUserInvited(state: Boolean): NextStep = {
    EitherT(callAnotherMicroserviceThatReturns(state)).flatMap {
      case isInvited if isInvited => completeWithUserState(Invited)
      case _ => continueRunningSteps
    }
  }

  private def isWithinDailyInviteLimit(state: Boolean): NextStep = {
    EitherT(callAnotherMicroserviceThatReturns(state)).flatMap {
      case reachedCapLimit if reachedCapLimit => completeWithUserState(NotEnrolled)
      case _ => continueRunningSteps
    }
  }

  private def inviteUser(): ErrorOrUserState = {
    EitherT.right[ErrorInfo](Future(())).map (_ => InvitedFirstTime)
  }


  def evaluateStateForUser(serviceResponses: ServiceResponses): ErrorOrUserState = {

    def step(runStep: => NextStep)(continue: => ErrorOrUserState): ErrorOrUserState = {
      runStep.flatMap {
        case Complete(state) => EitherT.pure[Future, ErrorInfo](state)
        case Continue => continue
      }
    }

    import serviceResponses._

    step(isAlreadyEnrolled(userIsAlreadyEnrolled)) {
      step(isUserInvited(userIsAlreadyInvited)) {
        step(isWithinDailyInviteLimit(isOverDailyInviteLimit)) {
          step(isUserEligibleToBeInvited(userIsEligible)) {
            inviteUser()
          }
        }
      }
    }
  }
}