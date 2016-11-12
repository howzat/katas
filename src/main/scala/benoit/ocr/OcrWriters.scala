package benoit.ocr

import scala.language.postfixOps

object OcrToDigitMap {

  private final val stringValues = Array(
    " _ | ||_|   ",
    "     |  |   ",
    " _  _||_    ",
    " _  _| _|   ",
    "   |_|  |   ",
    " _ |_  _|   ",
    " _ |_ |_|   ",
    " _   |  |   ",
    " _ |_||_|   ",
    " _ |_| _|   "
  )

  private val tuples = (0 to 9) zip stringValues
  final val digitsToRepr = tuples toMap
  final val reprToDigit = digitsToRepr.map(t => t._2 -> t._1)

}

trait OcrWriter[A <: Ocr, R] {
  def write(digit: OcrDigit): String

  def write(v: A): R
}

object DefaultOcrWriters {


  implicit val ocrNumberMatrixWriter: OcrWriter[OcrNumber, String] = {

    new OcrWriter[OcrNumber, String] {

      private val combined: PartialFunction[(String, String), String] = {
        case (a, b) => a + b
      }


      private def writeDigits(digits: Seq[OcrDigit], results: Seq[String]): Seq[String] = digits match {
        case Nil => results
        case x :: xs => writeDigits(xs, results.zip(lookupOcrString(x).grouped(3) toList).map(combined))
      }

      def lookupOcrString(x: OcrDigit): String = {
        OcrToDigitMap.digitsToRepr(x.intValue)
      }

      override def write(number: OcrNumber): String = {
        writeDigits(number.digits, Seq("", "", "", "")) mkString ("","\n", "\n")
      }

      override def write(number: OcrDigit): String = writeDigits(List(number), Seq("", "", "", "")) mkString "\n"
    }
  }
}