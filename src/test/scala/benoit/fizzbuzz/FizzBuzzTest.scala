package benoit.fizzbuzz


import org.scalatest.FunSuite
import org.scalatest.matchers.MustMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class FizzBuzzTest extends FunSuite with MustMatchers {

  def toFizzBuzz(i: Int): String = {
    def isFizz = i % 3 == 0
    def isBuzz = i % 5 == 0

    if(isFizz && isBuzz) "fizzbuzz"
    else if(isFizz) "fizz"
    else if(isBuzz) "buzz"
    else i + ""
  }

  def fizzBuzzSequence(numbers: Int*) :String = {
    (numbers map toFizzBuzz) mkString(",")
  }

  test("1 should return 1 because it is not a multiple of 3 or 5") {
    assert(toFizzBuzz(1) === "1")
  }

  test("3 should return fizz because it is not a multiple of 3") {
    assert(toFizzBuzz(3) === "fizz")
  }

  test("5 should return buzz because it is not a multiple of 5") {
    assert(toFizzBuzz(5) === "buzz")
  }

  test("10 should return buzz because it is not a multiple of 5") {
    assert(toFizzBuzz(10) === "buzz")
  }

  test("6 should return fizz because it is not a multiple of 3") {
    assert(toFizzBuzz(6) === "fizz")
  }

  test("15 should return fizzbuzz because it is a multiple of 3 and 5") {
    assert(toFizzBuzz(15) === "fizzbuzz")
  }

  test("test multiples of 5 to 1000 alway returns a sting containing buzz") {
    for (i <- 5 to 1000 by 5) {
      assert(toFizzBuzz(i) contains  "buzz", i)
    }
  }

  test("test multiples of 3 to 1000 alway returns a sting containing fizz") {
    for(i <- 3 to 1000 by 3) {
      assert(toFizzBuzz(i) contains "fizz", i)
    }
  }

  test("test multiples of 15 to 1000 alway returns fizzbuzz") {
    for(i <- 15 to 1000 by 15) {
      assert(toFizzBuzz(i) contains "fizzbuzz", i)
    }
  }

  test("fizzbuzz sequence") {
    assert( fizzBuzzSequence(1 to 15:_*) === "1,2,fizz,4,buzz,fizz,7,8,fizz,buzz,11,fizz,13,14,fizzbuzz" )
  }
}
