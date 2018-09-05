package invites

import benoit.invites._
import cats.Monad
import cats.implicits._
import org.scalatest.FlatSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class InviteServiceForFutureSpec extends FlatSpec with InviteServiceBehaviours[Future] {

  val inviteService = new InviteService[Future]()(implicitly[Monad[Future]])

  "A Future Based Invite Service" should behave like aWellBehavedImplementation(
    userState => Await.result(inviteService.evaluateStateForUser(userState).value, 20 seconds)
  )

  "A Future Based Invite Service" should behave like aServiceThatHandlesFailure(
    userState => Await.result(inviteService.evaluateStateForUser(userState).value, 20 seconds)
  )
}
