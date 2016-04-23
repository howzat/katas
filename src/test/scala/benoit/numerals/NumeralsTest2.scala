package benoit.numerals

import org.scalatest.FunSuite

import scala.annotation.tailrec

class NumeralsTest2 extends FunSuite with RomanNumeralsTestData {

  override def path= "src/test/scala/benoit/numerals/numerals.txt"

  def toNumeral(value: Int): String = {
    @tailrec
    def toNumeral(numerals:List[Numeral], result:Numeral) : Numeral = numerals match  {
      case Nil => result
      case numeral :: rest if(result.value >= numeral.value) => toNumeral(numerals, Numeral(result.symbol+numeral.symbol, result.value - numeral.value))
      case numeral :: rest => toNumeral(rest, result)
    }

    toNumeral(RomanNumerals, Numeral("", value)).symbol
  }

  forAll (RomanNumeralsDataTable) { numeral:Numeral =>
    test(s"the numernal for ${numeral.value} should be ${numeral.symbol}") {
      assert(toNumeral(numeral.value) === numeral.symbol, s"expected ${numeral.symbol} for ${numeral.value}, was ${toNumeral(numeral.value)}")
    }
  }
}

