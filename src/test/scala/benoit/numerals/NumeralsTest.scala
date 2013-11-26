package benoit.numerals

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class NumeralsTest extends FunSuite {

  val One    : String = "I"
  val Five   : String = "V"
  val Ten    : String = "X"
  val Fifty  : String = "L"
  val Hundred: String = "C"

  def numeralFor(digit: Int): String = {
    if(digit >= 100)     Hundred + numeralFor(digit - 100)
    else if(digit >= 90) numeralFor(10) + Hundred + numeralFor(digit - 90)
    else if(digit >= 50) Fifty + numeralFor(digit - 50)
    else if(digit == 40) numeralFor(10) + Fifty
    else if(digit >= 10) Ten + numeralFor(digit - 10)
    else if(digit == 9)  ones(1) + Ten
    else if(digit >= 5)  Five + ones(digit - 5)
    else if(digit == 4)  ones(1) + Five
    else if(digit > 0)   ones(digit)
    else ""
  }

  def ones(digit: Int): String = {
    (for(i <- 1 to digit by 1) yield { One }) mkString
  }

  test("range check 1..150") {

    for(digit <- 1 to 150 by 1) {
      println(numeralFor(digit))
    }
  }

  test("101 should be CI") {
    assert( numeralFor(101) === "CI")
  }

  test("98 should be XCVIII") {
    assert( numeralFor(98) === "XCVIII")
  }

  test("90 should be XC") {
    assert( numeralFor(90) === "XC")
  }

  test("80 should be LXXX") {
    assert( numeralFor(80) === "LXXX")
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

  test("14 should be XIV") {
    assert( numeralFor(14) === "XIV")
  }

  test("15 should be XV") {
    assert( numeralFor(15) === "XV")
  }

  test("30 should be XXX") {
    assert( numeralFor(30) === "XXX")
  }

  test("40 should be XL") {
    assert( numeralFor(40) === "XL")
  }

  test("50 should be L") {
    assert( numeralFor(50) === "L")
  }

  test("60 should be LX") {
    assert( numeralFor(60) === "LX")
  }

}
