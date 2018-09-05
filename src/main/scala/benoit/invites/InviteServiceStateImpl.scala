package benoit.invites

import cats._
import cats.data._
import cats.implicits._

class InviteServiceStateImpl[G[_]]()(implicit MonadOps: Monad[G]) extends EvaluateStateForUser[G] {

  private def isAlreadyEnrolled(userStateClient: UserStateClient[G], state:States): G[FlowControl] = {
    userStateClient.isAlreadyEnrolled().flatMap {
      case Right(enrolled) => MonadOps.pure(enrolled.foldBoolean[FlowControl](Complete(state))(Continue))
      case Left(errorInfo) => MonadOps.pure(Failed(errorInfo))
    }
  }

  private def isUserEligibleToBeInvited(userStateClient: UserStateClient[G], state:States): G[FlowControl] = {
    userStateClient.isUserEligibleToBeInvited().flatMap {
      case Right(isEligible) => MonadOps.pure((!isEligible).foldBoolean[FlowControl](Complete(NotEnrolled))(Continue))
      case Left(errorInfo)   => MonadOps.pure(Failed(errorInfo))
    }
  }

  private def isUserInvited(userStateClient: UserStateClient[G],state:States): G[FlowControl] = {
    userStateClient.isUserInvited().flatMap {
      case Right(isInvited) => MonadOps.pure(isInvited.foldBoolean[FlowControl](Complete(Invited))(Continue))
      case Left(errorInfo)  => MonadOps.pure(Failed(errorInfo))
    }
  }

  private def isWithinDailyInviteLimit(userStateClient: UserStateClient[G], state:States): G[FlowControl] = {
    userStateClient.isWithinDailyInviteLimit.flatMap {
      case Right(overLimit) => MonadOps.pure(overLimit.foldBoolean[FlowControl](Complete(NotEnrolled))(Continue))
      case Left(errorInfo)   => MonadOps.pure(Failed(errorInfo))
    }
  }

  private def inviteUser(state:States): G[FlowControl] = MonadOps.pure(Complete(state))

  private val step = (runNextState: () => G[FlowControl]) => StateT[G, FlowControl, Unit] {
    case Continue => runNextState().map((_,()))
    case other => MonadOps.pure(other, ())
  }

  override def evaluateStateForUser(userStateClient: UserStateClient[G]): ErrorOrUserState = {

    val statePipeline = for {
      _ <- step(()=>isAlreadyEnrolled(userStateClient, Enrolled))
      _ <- step(()=>isUserInvited(userStateClient, Invited))
      _ <- step(()=>isWithinDailyInviteLimit(userStateClient, NotEnrolled))
      _ <- step(()=>isUserEligibleToBeInvited(userStateClient, NotEnrolled))
      s <- step(()=>inviteUser(InvitedFirstTime))
    } yield s

    val finalState = statePipeline.runS(Continue).map {
      case Complete(state) => Right(state)
      case Failed(errorInfo) => Left(errorInfo)
      case other => Left(ErrorInfo(s"failed to run evaluation to a terminal state, result [$other]"))
    }

    EitherT(finalState)
  }
}