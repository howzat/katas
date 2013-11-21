package beniot.numerals

import org.scalatest.FunSuite

class NumeralsTest extends FunSuite {

  val One: String = "I"
  val Five: String = "V"
  val Ten: String = "X"

  def numeralFor(digit: Int) : String = {
    if(digit > 9) Ten
    else if(digit > 8) ones(digit -8) + Ten
    else if(digit > 5) Five + ones(digit -5)
    else if(digit > 4) Five
    else if(digit > 3) ones(1) + Five
    else ones(digit)
  }

  def ones(digit: Int): String = {
    (for(i <- 1 to digit by 1) yield { One }) mkString
  }

  test("1 should be I") {
    assert( numeralFor(1) === "I")
  }

  test("2 should be II") {
    assert( numeralFor(2) === "II")
  }

  test("4 should be IV") {
    assert( numeralFor(4) === "IV")
  }

  test("5 should be V") {
    assert( numeralFor(5) === "V")
  }

  test("6 should be VI") {
    assert( numeralFor(6) === "VI")
  }

  test("7 should be VII") {
    assert( numeralFor(7) === "VII")
  }

  test("9 should be IX") {
    assert( numeralFor(9) === "IX")
  }

  test("10 should be X") {
    assert( numeralFor(10) === "X")
  }

  test("14 should be XIII") {
    assert( numeralFor(10) === "X")
  }
}
