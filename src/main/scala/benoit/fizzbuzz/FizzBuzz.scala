package benoit.fizzbuzz

object FizzBuzz {

  def toFizzBuzz(digit: Int): String = {
    def isFizz = digit % 3 == 0
    def isBuzz = digit % 5 == 0

    if(isFizz && isBuzz) "fizzbuzz"
    else if(isFizz) "fizz"
    else if(isBuzz) "buzz"
    else digit + ""
  }

  def fizzBuzzSequence(numbers: Int*): String = {
    (numbers map toFizzBuzz) mkString (",")
  }
}
