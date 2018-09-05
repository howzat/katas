package benoit

package object invites {

  implicit class BooleanOps(val self: Boolean) {
    def mapTrue[B](f: => B): Option[B] = if(self) Some(f) else None
    def mapFalse[B](f: => B): Option[B] = if(!self) Some(f) else None
    def foldBoolean[A](fa: => A)(fb: => A) = if(self) fa else fb
  }

  case class ErrorInfo(message:String)

  sealed trait States
  case object NotEnrolled extends States
  case object InvitedFirstTime extends States
  case object Invited extends States
  case object Enrolled extends States

  sealed trait FlowControl
  case class Complete(state: States) extends FlowControl
  case object Continue extends FlowControl
  case object Start extends FlowControl
  case class Failed(errorInfo: ErrorInfo) extends FlowControl
}
