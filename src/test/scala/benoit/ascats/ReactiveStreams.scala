package benoit.ascats

import benoit.UnitSpec
import benoit.ascats.typeclasses.PrintersImplementations._
import benoit.ascats.typeclasses.{Person, PrinterApi, PrinterSyntax}

import scala.language.postfixOps


class ReactiveStreams extends UnitSpec {

  sealed trait EventStream[A] {

    import EventStream._

    def next : Option[A] = this match {
      case Map(source, f) => source.next.map(f)
      case FlatMap(source, f) => source.next.map(f).flatMap(_.next)
      case FromIterator(iterator) => if(iterator.isEmpty) None else Some(iterator.next())
    }

    def map[B](f: A => B): EventStream[B] = Map(this, f)

    def flatMap[B](f: A => EventStream[B]): EventStream[B] = FlatMap(this, f)

    def foldLeft[B](b: B)(op: (B, A) => B): B = {
      next match {
        case None => b
        case Some(v) => foldLeft(op(b, v))(op)
      }
    }
  }

  object EventStream {
    def fromIterator[A](iterator: Iterator[A]) : EventStream[A] = FromIterator(iterator)
  }

  final case class FromIterator[A] (a: Iterator[A]) extends EventStream[A]
  final case class Map[A,B] (source: EventStream[A], f: A => B) extends EventStream[B]
  final case class FlatMap[A,B] (source: EventStream[A], f: A => EventStream[B]) extends EventStream[B]


  "EventStreaming" - {

    val numbers = (1 to 10).toList
    val stream  = EventStream.fromIterator[Int](numbers iterator)

    val result = stream.foldLeft[Int](0)( _ + _ )

    result shouldBe numbers.sum
  }
}




































