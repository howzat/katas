package benoit.invites

import cats.Monad
import cats.data._

class InviteService[G[_]]()(implicit MonadOps: Monad[G]) extends EvaluateStateForUser[G] {

  private def continueRunningSteps: NextStep = {
    EitherT.right[ErrorInfo](MonadOps.pure(Continue))
  }

  private def completeWithUserState(state: States): NextStep = {
    EitherT.right[ErrorInfo](MonadOps.pure(Complete(state)))
  }

  private def isAlreadyEnrolled(userStateClient: UserStateClient[G]): NextStep = {
    EitherT(userStateClient.isAlreadyEnrolled).flatMap {
      case isEnrolled if isEnrolled => completeWithUserState(Enrolled)
      case _ => continueRunningSteps
    }
  }

  private def isUserEligibleToBeInvited(userStateClient: UserStateClient[G]): NextStep = {
    EitherT(userStateClient.isUserEligibleToBeInvited).flatMap {
      case isEligible if isEligible => continueRunningSteps
      case _ => completeWithUserState(NotEnrolled)
    }
  }

  private def isUserInvited(userStateClient: UserStateClient[G]): NextStep = {
    EitherT(userStateClient.isUserInvited).flatMap {
      case isInvited if isInvited => completeWithUserState(Invited)
      case _ => continueRunningSteps
    }
  }

  private def isWithinDailyInviteLimit(userStateClient: UserStateClient[G]): NextStep = {
    EitherT(userStateClient.isWithinDailyInviteLimit).flatMap {
      case reachedCapLimit if reachedCapLimit => completeWithUserState(NotEnrolled)
      case _ => continueRunningSteps
    }
  }

  private def inviteUser(): ErrorOrUserState = {
    EitherT.right[ErrorInfo](MonadOps.pure(InvitedFirstTime))
  }

  override def evaluateStateForUser(userStateClient: UserStateClient[G]): ErrorOrUserState = {

    def step(runStep: => NextStep)(continue: => ErrorOrUserState): ErrorOrUserState = {
      runStep.flatMap {
        case Complete(state) => EitherT.right[ErrorInfo](MonadOps.pure(state))
        case Continue => continue
        case Failed(_) => continue
      }
    }

    step(isAlreadyEnrolled(userStateClient)) {
      step(isUserInvited(userStateClient)) {
        step(isWithinDailyInviteLimit(userStateClient)) {
          step(isUserEligibleToBeInvited(userStateClient)) {
            inviteUser()
          }
        }
      }
    }
  }
}