package benoit.numerals

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, FunSpec}
import scala.io.Source
import System._


@RunWith(classOf[JUnitRunner])
class NumeralsTest2 extends FunSuite {

  case class Numeral(symbol: String, value: Int)

  val filePath = "src/test/scala/benoit/numerals/numerals.txt"

  val numeralTestData = {
    (Source fromFile filePath getLines() map (_ split (",") toList) toList) map {
      case value :: symbol :: Nil => Numeral(symbol.trim, Integer.parseInt(value.trim))
      case _ => fail("couldn't initialise numeral table data")
    }
  }

  val numerals = Seq(
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
                    )


  def toNumeral(value: Int): String = {


  }

  numeralTestData take (2) map {
    case Numeral(symbol, value) =>
      test(s"the numernal for $value should be $symbol") {
        assert(toNumeral(value) == symbol, s"expected $symbol for $value, was ${toNumeral(value)}")
      }
  }


}
