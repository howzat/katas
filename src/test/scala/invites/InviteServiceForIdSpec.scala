package invites

import benoit.invites._
import cats.{Id, Monad}
import org.scalatest.FlatSpec

class InviteServiceForIdSpec extends FlatSpec with InviteServiceBehaviours[Id] {

  val inviteService = new InviteService()(implicitly[Monad[Id]])

  "A Monad[Id] Based Invite Service" should behave like aWellBehavedImplementation(
    userState => inviteService.evaluateStateForUser(userState).value
  )

  "A Monad[Id] Based Invite Service" should behave like aServiceThatHandlesFailure(
    userState => inviteService.evaluateStateForUser(userState).value
  )
}
