package cats

import benoit.UnitSpec
import benoit.cats.InviteService._
import benoit.cats._

import scala.concurrent.Future

class BasicInviteServiceSpec extends UnitSpec {

  "evaluateStateForUser" - {

    "returns InvitedFirstTime" in new ServiceResponses {

        override def userIsAlreadyEnrolled: Boolean = false
        override def userIsEligible: Boolean = true
        override def userIsAlreadyInvited: Boolean = false
        override def isOverDailyInviteLimit: Boolean = false

      whenReady(evaluateStateForUser(this)){
        _ shouldBe Right(InvitedFirstTime)
      }
    }

    "returns Invited the user is already invited" in new ServiceResponses {

      override def userIsAlreadyEnrolled: Boolean = false
      override def userIsAlreadyInvited:Boolean = true
      override def userIsEligible: Boolean = thisShouldNeverBeCalled("UserIsEligible")
      override def isOverDailyInviteLimit:Boolean = thisShouldNeverBeCalled("IsOverDailyInviteLimit")

      whenReady(evaluateStateForUser(this)) {
        _ shouldBe Right(Invited)
      }
    }

    "returns NotEnrolled if the user is not enrolled" in new ServiceResponses {

      override def userIsAlreadyEnrolled: Boolean = false
      override def userIsAlreadyInvited:Boolean = false
      override def userIsEligible: Boolean = true
      override def isOverDailyInviteLimit:Boolean = true

      whenReady(evaluateStateForUser(this)) {
        _ shouldBe Right(NotEnrolled)
      }
    }

    "returns NotEnrolled if is over the daily limit" in new ServiceResponses {

      override def userIsAlreadyEnrolled: Boolean = false
      override def userIsEligible: Boolean = false
      override def userIsAlreadyInvited:Boolean = false
      override def isOverDailyInviteLimit: Boolean = true

      whenReady(evaluateStateForUser(this)) {
        _ shouldBe Right(NotEnrolled)
      }
    }

    "returns Enrolled if the user is already enrolled" in new ServiceResponses {

      override def userIsAlreadyEnrolled: Boolean = true
      override def userIsEligible: Boolean = thisShouldNeverBeCalled("UserIsEligible")
      override def userIsAlreadyInvited:Boolean = thisShouldNeverBeCalled("UserIsAlreadyInvited")
      override def isOverDailyInviteLimit: Boolean = thisShouldNeverBeCalled("IsOverDailyInviteLimit")

      whenReady(evaluateStateForUser(this)) {
        _ shouldBe Right(Enrolled)
      }
    }
  }

  private def evaluateStateForUser(serviceResponses: ServiceResponses): Future[Either[ErrorInfo, States]] = {
    new BasicInviteService().evaluateStateForUser(serviceResponses).value
  }

  private def thisShouldNeverBeCalled(s:String) :  Boolean = {
    throw new RuntimeException(s"This call [$s] should never be made!")
  }
}
