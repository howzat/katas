package cats

import benoit.UnitSpec
import benoit.cats.InviteService._
import benoit.cats._

class BasicInviteServiceSpec extends UnitSpec {

  private def thisShouldNeverBeCalled(s:String) :  Boolean = {
    throw new RuntimeException(s"This call [$s] should never be made!")
  }

  "evaluateStateForUser" - {

    "returns InvitedFirstTime" in new ServiceResponses {

        override def userIsAlreadyEnrolled: Boolean = false
        override def userIsEligible: Boolean = true
        override def userIsAlreadyInvited: Boolean = false
        override def isOverDailyInviteLimit: Boolean = false

      whenReady(new BasicInviteService().evaluateStateForUser(this).value){
        _ shouldBe Right(InvitedFirstTime)
      }
    }

    "returns Invited the user is already invited" in new ServiceResponses {

      override def userIsAlreadyEnrolled: Boolean = false
      override def userIsAlreadyInvited:Boolean = true
      override def userIsEligible: Boolean = thisShouldNeverBeCalled("UserIsEligible")
      override def isOverDailyInviteLimit:Boolean = thisShouldNeverBeCalled("IsOverDailyInviteLimit")

      whenReady(new BasicInviteService().evaluateStateForUser(this).value) {
        _ shouldBe Right(Invited)
      }
    }

    "returns NotEnrolled if the user is not enrolled" in new ServiceResponses {

      override def userIsAlreadyEnrolled: Boolean = false
      override def userIsAlreadyInvited:Boolean = false
      override def userIsEligible: Boolean = true
      override def isOverDailyInviteLimit:Boolean = true

      whenReady(new BasicInviteService().evaluateStateForUser(this).value) {
        _ shouldBe Right(NotEnrolled)
      }
    }

    "returns NotEnrolled if is over the daily limit" in new ServiceResponses {

      override def userIsAlreadyEnrolled: Boolean = false
      override def userIsEligible: Boolean = false
      override def userIsAlreadyInvited:Boolean = false
      override def isOverDailyInviteLimit: Boolean = true

      whenReady(new BasicInviteService().evaluateStateForUser(this).value) {
        _ shouldBe Right(NotEnrolled)
      }
    }

    "returns Enrolled if the user is already enrolled" in new ServiceResponses {

      override def userIsAlreadyEnrolled: Boolean = true
      override def userIsEligible: Boolean = thisShouldNeverBeCalled("UserIsEligible")
      override def userIsAlreadyInvited:Boolean = thisShouldNeverBeCalled("UserIsAlreadyInvited")
      override def isOverDailyInviteLimit: Boolean = thisShouldNeverBeCalled("IsOverDailyInviteLimit")

      whenReady(new BasicInviteService().evaluateStateForUser(this).value) {
        _ shouldBe Right(Enrolled)
      }
    }
  }

  /*
  override def UserIsAlreadyEnrolled:Boolean = thisShouldNeverBeCalled("UserIsAlreadyEnrolled")
      override def UserIsEligible:Boolean = thisShouldNeverBeCalled("UserIsEligible")
      override def UserIsAlreadyInvited:Boolean = thisShouldNeverBeCalled("UserIsAlreadyInvited")
      override def IsOverDailyInviteLimit:Boolean = thisShouldNeverBeCalled("IsOverDailyInviteLimit")
   */
}
