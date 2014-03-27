package benoit.hackzone


import org.scalatest.{FreeSpec, FunSuite}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.annotation.tailrec
import scala.concurrent.{Await, ExecutionContext, Future}
import akka.actor.ActorSystem
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import org.junit.Test
import org.scalatest.matchers.MustMatchers
import scala.xml._
import scala.Some
import scala.xml.NamespaceBinding

@RunWith(classOf[JUnitRunner])
class Hackzone extends FreeSpec with MustMatchers  {


}
