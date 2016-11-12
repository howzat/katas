package benoit.ocr

import scala.language.postfixOps

sealed trait Ocr {
  val value: String
}

sealed trait OcrDigit extends Ocr {
  override def toString: String = s"OcrDigit($value)"
  def intValue:Int
}


sealed trait OcrNumber extends Ocr {
  def digits: Seq[OcrDigit]

  override def toString: String = s"OcrNumber($digits)"

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case number: OcrNumber => number.digits.equals(this.digits)
      case _ => false
    }
  }
}

object OcrDigit {

  final val ocrDigits = (0 to 9) map (v => new OcrDigit {
    override val value: String = Character.forDigit(v, 10) toString
    override val intValue: Int = v
  })

  def apply(digit: Int): OcrDigit = {
    require(digit < 10 && digit >= 0, s"[$digit]! ocr digits must be positive integers between 0 and 9")
    ocrDigits(digit)
  }

  def apply(repr: String): OcrDigit = ocrDigits(OcrToDigitMap reprToDigit repr)

  val zero: OcrDigit = OcrDigit(0)
}


object OcrNumber {

  def apply(v: String): OcrNumber = new OcrNumber {

    require(v.nonEmpty, "Can't build an OcrNumber from an empty string")

    override def digits: Seq[OcrDigit] =
      value.foldRight(Seq.empty[OcrDigit]) {
        case (c, dgs) => OcrDigit(c asDigit) +: dgs
      }

    override val value: String = v
  }

  def apply(v: Seq[OcrDigit]): OcrNumber = new OcrNumber {

    require(v.nonEmpty, "Can't build an OcrNumber from an empty Seq")

    override def digits: Seq[OcrDigit] = v

    override val value: String = v.mkString
  }

  def apply(): OcrNumber = OcrNumber("0")
}
