package benoit.loanrates



import org.scalacheck.Gen
import org.scalatest.FlatSpec
import org.scalatest.MustMatchers._
import org.scalatest.prop.PropertyChecks
import org.scalacheck.Gen.{Choose, choose}

import scala.math.BigDecimal.RoundingMode


class InterestRateCalculatorSpec extends FlatSpec with PropertyChecks {


  def calculateStandardInterestRate(balance: BigDecimal): BigDecimal = balance match {
    case _ if balance < 0 => 0.0
    case _ if balance >= 0 && balance <= 1000.0 => 0.01
    case _ if balance > 1000.0 && balance <= 2000.0 => 0.02
    case _ => 0.03
  }

  val standardRate = calculateStandardInterestRate _

  def applyInterest(balance:BigDecimal, interestRate: (BigDecimal) => BigDecimal) : BigDecimal = {
    (balance + (balance * interestRate(balance))).setScale(2, RoundingMode.HALF_DOWN)
  }

  implicit def toBigdecimalGenerator = Choose.xmap[Double, BigDecimal](
    from => BigDecimal(from), bgDecimal => bgDecimal.doubleValue()
  )

  val positiveBalances: Gen[BigDecimal] = choose(0, 3001.0)

  val negativeOrEmptyBalances: Gen[BigDecimal] = choose(-10.0,0)

  "An InterestRateCalculator" should "return an interest rate of 0 whenever the balance is negative" in {
    forAll( negativeOrEmptyBalances ) { balance =>
      whenever(balance < BigDecimal(0.0)) {
        calculateStandardInterestRate(balance) must equal(0.0)
      }
    }
  }

  it should "return an interest rate of 0.1 whenever the balance is greater than 0 and less than 1000.0" in {
    forAll( positiveBalances ) { balance =>
      whenever(balance >= BigDecimal(0.0) && balance <= BigDecimal(1000.0)) {
        calculateStandardInterestRate(balance) must equal(0.01)
      }
    }
  }

  it should "return an interest rate of 0.2 whenever the balance is greater than 1000 and less than 2000.0" in {
    forAll( positiveBalances ) { balance =>
      whenever(balance > BigDecimal(1000.0) && balance <= BigDecimal(2000.0)) {
        calculateStandardInterestRate(balance) must equal(0.02)
      }
    }
  }

  it should "return an interest rate of 0.3 whenever the balance is greater than 2000.0" in {
    forAll( positiveBalances ) { balance =>
      whenever(balance > BigDecimal(2000.0)) {
        calculateStandardInterestRate(balance) must equal(0.03)
      }
    }
  }

  "A bank account" should "earn £5 on a balance of £500" in {
    applyInterest(500.0, standardRate) must equal(505.0)
  }

  it should "apply interest to the correct precision" in {
    applyInterest(1234.56, standardRate) must equal(1259.25)
  }
}