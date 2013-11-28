package benoit.numerals

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LoanRatesTest extends FunSuite {

  def getInterestRateFor(i: Int): Float = {
    if(i >= 0 && i < 1000) 0.01F
    else if(i >= 1000 && i < 5000) 0.02F
    else 0.03F
  }

  def getInterest(i: Int): Float = {
    val interestRate = getInterestRateFor(i)
    i * interestRate
  }

  test("Given an account balance of £500, the interest should be £5") {
    assert(getInterest(500) === 5.0F)
  }

  test("Given an account balance over £5000 the interest to apply is 3%") {
    assertInterestToApply(5000 to 10000 by 1, 0.03F)
  }

  test("Given an account balance between £1000 and £4999, the interest to apply is 2%") {
    assertInterestToApply(1000 to 4999 by 1, 0.02F)
  }

  test("Given an account balance between £0 and £999, the interest to apply is 1%") {
    assertInterestToApply(0 to 999 by 1, 0.01F)
  }

  private def assertInterestToApply(range: Range, expected: Float) {
    for(amount <- range) {
      assert(getInterestRateFor(amount) === expected, s"failed $amount")
    }
  }

  def toPence(pounds: Int) = pounds * 100
}
