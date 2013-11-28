package benoit.numerals

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class NumeralsTest extends FunSuite {

  def toNumeral(digit: Int): String = {
    if(digit >= 500) "D" + toNumeral(digit - 500)
    else if(digit >= 400) "CD" + toNumeral(digit - 400)
    else if(digit >= 100) "C" + toNumeral(digit - 100)
    else if(digit >= 90) "XC" + toNumeral(digit - 90)
    else if(digit >= 50) "L" + toNumeral(digit - 50)
    else if(digit >= 40) "XL" + toNumeral(digit - 40)
    else if(digit >= 10) "X" + toNumeral(digit - 10)
    else if(digit >= 9) "IX" + toNumeral(digit - 9)
    else if(digit >= 5) "V" + toNumeral(digit - 5)
    else if(digit >= 4) "IV" + toNumeral(digit - 4)
    else (for(i <- 1 to digit by 1) yield "I") mkString
  }

  test("501 should be D") {
    assert(toNumeral(501) === "DI")
  }

  test("500 should be D") {
    assert(toNumeral(500) === "D")
  }

  test("400 should be CC") {
    assert(toNumeral(400) === "CD")
  }

  test("200 should be CC") {
    assert(toNumeral(200) === "CC")
  }

  test("48 should be XLVIII") {
    assert(toNumeral(48) === "XLVIII")
  }

  test("1 should be I") {
    assert(toNumeral(1) === "I")
  }

  test("2 should be II") {
    assert(toNumeral(2) === "II")
  }

  test("4 should be IV") {
    assert(toNumeral(4) === "IV")
  }

  test("5 should be V") {
    assert(toNumeral(5) === "V")
  }

  test("6 should be VI") {
    assert(toNumeral(6) === "VI")
  }

  test("7 should be VII") {
    assert(toNumeral(7) === "VII")
  }

  test("9 should be IX") {
    assert(toNumeral(9) === "IX")
  }

  test("10 should be X") {
    assert(toNumeral(10) === "X")
  }

  test("14 should be XIV") {
    assert(toNumeral(14) === "XIV")
  }

  test("15 should be XV") {
    assert(toNumeral(15) === "XV")
  }

  test("30 should be XXX") {
    assert(toNumeral(30) === "XXX")
  }

  test("40 should be XL") {
    assert(toNumeral(40) === "XL")
  }

  test("50 should be L") {
    assert(toNumeral(50) === "L")
  }

  test("60 should be LX") {
    assert(toNumeral(60) === "LX")
  }

  test("98 should be XCVIII") {
    assert(toNumeral(98) === "XCVIII")
  }

  test("90 should be XC") {
    assert(toNumeral(90) === "XC")
  }

  test("80 should be LXXX") {
    assert(toNumeral(80) === "LXXX")
  }


}
