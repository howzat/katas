package benoit.ocr

import scala.language.postfixOps


trait OcrReader[A, T] {
  def read(v: List[A]): List[T]
}

object DefaultOcrReaders {

  implicit val ocrFileReader = new OcrReader[String, OcrNumber] {
    override def read(v: List[String]): List[OcrNumber] = {
      v.iterator.sliding(4, 4) map toDigits toList
    }
  }

  def toDigits(readLines: Seq[String]): OcrNumber = {

    require(readLines.size == 4, s"Unparsable account number $readLines")

    val top = readLines.head.grouped(3)
    val middle = readLines(1).grouped(3)
    val bottom = readLines(2).grouped(3)
    val gutter = readLines(3).grouped(3)

    OcrNumber(top.map(t => OcrDigit(t + middle.next() + bottom.next() + gutter.next())) toList)
  }
}

