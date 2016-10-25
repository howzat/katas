package benoit.fpinscala

import benoit.UnitSpec
import org.scalatest.{Matchers, WordSpec}

import scala.util.Try


class SequenceOptions extends WordSpec with Matchers {


  def identity[A](a:A) = a

  def sequence[A](a: List[Option[A]]): Option[List[A]] = traverse(a)(identity)

  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] =  a match {
    case Nil => Some(Nil)
    case head :: tail => f(head) flatMap {
      b => traverse(tail)(f) map (List(b) ++ _)
    }
  }


  "traverse" should {

    "turn a list of string to a list of int" in {

      val result = traverse(List("1", "2", "3")) {
        case elem => Try(elem.toInt).toOption
      }

      result should be (Some(List(1,2,3)))
    }


    "turn a list of in into a list of string" in {

      val result = traverse(List(1, 2, 3)) {
        case elem => Option(elem.toString)
      }

      result should be (Some(List("1","2","3")))
    }

    "return None if any of the elements fail" in {

      val result = traverse(List(1,2,3,0)) {
        case elem => Try(10 / elem).toOption
      }

      result should be (None)
    }
  }


  "sequence" should {

    "turn a list of Options into an option of a Sequence" in {

      val result:Option[Seq[Int]] = sequence(List(Some(1),Some(2),Some(3),Some(4)))
      result should be (Some(Seq(1,2,3,4)))
    }

    "return None if any of the members are None" in {

      val result:Option[Seq[Int]] = sequence(List(Some(1),None,Some(3),Some(4)))
      result should be (None)
    }
  }
}
