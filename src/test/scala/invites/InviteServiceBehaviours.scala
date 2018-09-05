package invites

import benoit.invites._
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

trait InviteServiceBehaviours[T[_]] extends FlatSpec with InviteServiceUserJourneyFixtures[T] {
  this: FlatSpec =>

  type RunEvaluation = UserStateClient[T] => Either[ErrorInfo, States]

  def aWellBehavedImplementation(evaluateState: => RunEvaluation): Unit = {

    it should "return InvitedFirstTime when all conditions are met" in
      inviteScenario(StateInvitedFirstTime) {
        state => evaluateState(state) shouldBe Right(InvitedFirstTime)
      }

    it should "returns InvitedFirstTime when all conditions are met" in
      inviteScenario(StateInvitedFirstTime) {
        state => evaluateState(state) shouldBe Right(InvitedFirstTime)
      }

    it should "returns Enrolled if the user is already enrolled" in
      inviteScenario(StateAlreadyEnrolled) {
        state => evaluateState(state) shouldBe Right(Enrolled)
      }

    it should "returns Invited when the user is already invited" in
      inviteScenario(StateAlreadyInvited) {
        state => evaluateState(state) shouldBe Right(Invited)
      }

    it should "returns NotEnrolled when the daily cap has been exceeded" in
      inviteScenario(StateOverDailyInviteLimit) {
        state => evaluateState(state) shouldBe Right(NotEnrolled)
      }

    it should "returns NotEnrolled if the user is not eligible" in
      inviteScenario(StateIsNotEligible) {
        state => evaluateState(state) shouldBe Right(NotEnrolled)
      }
  }

  def aServiceThatHandlesFailure(evaluateState: => RunEvaluation): Unit = {
    it should "returns ErrorInfo when determining the enrolment check fails" in
      inviteScenario(FetchingEnrollmentStatusFails) {
        state => evaluateState(state) shouldBe Left(state.errorMessageDefinedForState)
      }
    it should "returns ErrorInfo when determining the invite status fails" in
      inviteScenario(FetchingInviteStatusFails) {
        state => evaluateState(state) shouldBe Left(state.errorMessageDefinedForState)
      }
    it should "returns ErrorInfo when determining the daily limit status fails" in
      inviteScenario(FetchingDailyLimitFails) {
        state => evaluateState(state) shouldBe Left(state.errorMessageDefinedForState)
      }
    it should "returns ErrorInfo when determining the eligibility status fails" in
      inviteScenario(FetchingEligibilityStatusFails) {
        state => evaluateState(state) shouldBe Left(state.errorMessageDefinedForState)
      }
  }
}
