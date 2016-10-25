package benoit.ocr

sealed trait Ocr

trait OcrDigit extends Ocr {
  val digit: Int
}

object OcrDigit {

  final val digits = (0 to 9) map (value => new OcrDigit {
    override val digit: Int = value
  })

  // TODO: make smarter to build arbitrary length numbers
  def apply(digit: Int): OcrDigit = {
    digits(digit)
  }
}

final case class OcrAccountNumber(digits: Int*) extends Ocr