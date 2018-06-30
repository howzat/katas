package household

import benoit.UnitSpec

import scala.io.Source

object  Entry {

  def apply(s:String) : Entry = {
    val split = s.split(",")
    new Entry(split(0),split(1),split(2),split(3),split(4),split(5))
  }
}

case class Entry(date:String, transaction:String, description:String, out:String, in:String, tag:String)

class Household extends UnitSpec {

  val csv = Source.fromFile("resources/JointStatement.csv").toStream

  val entries : List[Entry] = Nil


  def nextCell(text:Stream[Char], captured:String)(f:Char => Boolean) : (String, Stream[Char]) = text match {
    case Stream.Empty  => (captured, Stream.Empty)
    case head #:: tail if f(head)  => (captured, tail)
    case head #:: tail => nextCell(tail, head + captured)(f)
  }

  def parseCells(text:Stream[Char], cells:Seq[String] = Nil) : Seq[String] = text match {
    case Stream.Empty  => cells.reverse
    case _ =>
      val (cell, remainder) = nextCell(text, "")(_.equals(','))

      println(cell)
      parseCells(remainder, cell +: cells)
  }


  "Parse Household" - {
    "save me money!" in {
      parseCells(csv)
    }
  }
}
