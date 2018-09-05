package benoit.invites

import cats.data.EitherT

trait EvaluateStateForUser[G[_]] {

  type ErrorOrUserState = EitherT[G, ErrorInfo, States]
  type NextStep = EitherT[G, ErrorInfo, FlowControl]

  def evaluateStateForUser(userStateClient: UserStateClient[G]): ErrorOrUserState
}