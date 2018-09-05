package invites

import benoit.invites.{ErrorInfo, UserStateClient}

trait InviteServiceUserJourneyFixtures[T[_]] {

  def inviteScenario(userStateClient:UserStateClient[T])(runTest:UserStateClient[T]=>Any): Unit =
    runTest(userStateClient)

  val StateInvitedFirstTime: UserStateClient[T] = new UserStateClient[T] {
    override def isUserAlreadyEnrolled  = Right(false)
    override def isUserAlreadyInvited   = Right(false)
    override def isOverDailyInviteLimit = Right(false)
    override def isUserEligible         = Right(true)
  }

  val StateAlreadyEnrolled: UserStateClient[T] = new UserStateClient[T] {
    override def isUserAlreadyEnrolled  = Right(true)
    override def isUserAlreadyInvited   = neverCallThis("isUserAlreadyInvited")
    override def isOverDailyInviteLimit = neverCallThis("isOverDailyInviteLimit")
    override def isUserEligible         = neverCallThis("isUserEligible")
  }

  val FetchingEnrollmentStatusFails: UserStateClient[T] =  new UserStateClient[T] {
    override val maybeErrorInfo = Some(ErrorInfo("Failed calling isUserAlreadyEnrolled"))
    override def isUserAlreadyEnrolled = Left(maybeErrorInfo.get)
    override def isUserAlreadyInvited = Right(false)
    override def isOverDailyInviteLimit = Right(false)
    override def isUserEligible = Right(true)
  }

  val FetchingInviteStatusFails: UserStateClient[T] =  new UserStateClient[T] {
    override val maybeErrorInfo = Some(ErrorInfo("Failed calling isUserAlreadyInvited"))
    override def isUserAlreadyEnrolled = Right(false)
    override def isUserAlreadyInvited = Left(errorMessageDefinedForState)
    override def isOverDailyInviteLimit = Right(false)
    override def isUserEligible = Right(true)
  }

  val StateAlreadyInvited: UserStateClient[T] = new UserStateClient[T] {
    override def isUserAlreadyEnrolled  = Right(false)
    override def isUserAlreadyInvited   = Right(true)
    override def isOverDailyInviteLimit = neverCallThis("IsOverDailyInviteLimit")
    override def isUserEligible         = neverCallThis("UserIsEligible")
  }

  val FetchingDailyLimitFails: UserStateClient[T] = new UserStateClient[T] {
    override val maybeErrorInfo = Some(ErrorInfo("Failed calling isOverDailyInviteLimit"))
    override def isUserAlreadyEnrolled  = Right(false)
    override def isUserAlreadyInvited   = Right(false)
    override def isOverDailyInviteLimit = Left(errorMessageDefinedForState)
    override def isUserEligible         = Right(true)
  }

  val StateOverDailyInviteLimit: UserStateClient[T] = new UserStateClient[T] {
    override def isUserAlreadyEnrolled  = Right(false)
    override def isUserAlreadyInvited   = Right(false)
    override def isOverDailyInviteLimit = Right(true)
    override def isUserEligible         = neverCallThis("UserIsEligible")
  }

  val FetchingEligibilityStatusFails: UserStateClient[T] = new UserStateClient[T] {
    override val maybeErrorInfo = Some(ErrorInfo("Failed calling isUserEligible"))
    override def isUserAlreadyEnrolled  = Right(false)
    override def isUserAlreadyInvited   = Right(false)
    override def isOverDailyInviteLimit = Right(false)
    override def isUserEligible         = Left(errorMessageDefinedForState)
  }

  val StateIsNotEligible: UserStateClient[T] = new UserStateClient[T] {
    override def isUserAlreadyEnrolled  = Right(false)
    override def isUserAlreadyInvited   = Right(false)
    override def isOverDailyInviteLimit = Right(false)
    override def isUserEligible         = Right(false)
  }
}
