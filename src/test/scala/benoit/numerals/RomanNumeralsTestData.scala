package benoit.numerals

import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}

import scala.io.Source

trait RomanNumeralsTestData extends TableDrivenPropertyChecks {

  case class Numeral(symbol: String, value: Int)

  def path: String

  def testData: List[Option[Numeral]] = {
    (Source fromFile path getLines() map (_ split (",") toList) toList) map {
      case value :: symbol :: Nil => Some(Numeral(symbol.trim, Integer.parseInt(value.trim)))
      case _ => println(s"couldn't initialise numeral table data from $path"); None
    }
  }

  val RomanNumeralsDataTable: TableFor1[Numeral] = Table("numerals", testData.flatten:_*)

  val RomanNumerals = List(
    Numeral("I", 1),
    Numeral("IV", 4),
    Numeral("V", 5),
    Numeral("IX", 9),
    Numeral("X", 10),
    Numeral("XL", 40),
    Numeral("L", 50),
    Numeral("XC", 90),
    Numeral("C", 100),
    Numeral("CD", 400),
    Numeral("D", 500),
    Numeral("CM", 900),
    Numeral("M", 1000)
  ).reverse
}


