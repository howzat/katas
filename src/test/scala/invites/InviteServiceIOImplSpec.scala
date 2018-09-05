package invites

import benoit.invites._
import cats.{Id, Monad}
import org.scalatest.FlatSpec

class InviteServiceIOImplSpec extends FlatSpec with InviteServiceBehaviours[Id] {

  val inviteService = new InviteServiceIOImpl()(implicitly[Monad[Id]])

  "A Monad[Id] with IO[G[_]] Based Invite Service" should behave like aWellBehavedImplementation(
    userState => inviteService.evaluateStateForUser(userState).value
  )

  "A Monad[Id] with IO[G[_]] Based Invite Service" should behave like aServiceThatHandlesFailure(
    userState => inviteService.evaluateStateForUser(userState).value
  )
}
