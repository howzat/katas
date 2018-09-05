package benoit.invites

import cats.Monad

trait UserStateClient[F[_]] {

  type ErrorOrBoolean = F[Either[ErrorInfo, Boolean]]

  def neverCallThis(s:String): Either[ErrorInfo, Boolean] =
    throw new RuntimeException(s"This should never be called! [$s]")

  protected def isUserAlreadyEnrolled:Either[ErrorInfo, Boolean] = Right(false)
  protected def isUserAlreadyInvited:Either[ErrorInfo, Boolean]  = Right(false)
  protected def isOverDailyInviteLimit:Either[ErrorInfo, Boolean] = Right(false)
  protected def isUserEligible:Either[ErrorInfo, Boolean] = Right(false)

  def maybeErrorInfo:Option[ErrorInfo] = None

  def errorMessageDefinedForState: ErrorInfo = maybeErrorInfo.getOrElse(
    throw new RuntimeException("No error information defined in this state")
  )

  def isAlreadyEnrolled()(implicit FMonad:Monad[F]): ErrorOrBoolean =
    FMonad.pure(isUserAlreadyEnrolled)

  def isUserEligibleToBeInvited()(implicit FMonad:Monad[F]): ErrorOrBoolean =
    FMonad.pure(isUserEligible)

  def isUserInvited()(implicit FMonad:Monad[F]): ErrorOrBoolean =
    FMonad.pure(isUserAlreadyInvited)

  def isWithinDailyInviteLimit()(implicit FMonad:Monad[F]): ErrorOrBoolean =
    FMonad.pure(isOverDailyInviteLimit)
}
