package benoit.ocr

import benoit.UnitSpec

import scala.language.postfixOps
import DefaultOcrWriters.printMatrixWriter
import OcrSyntax._

class OcrNumberSpec extends UnitSpec {

  val expectedValues = List(
    Array(' ', '_', ' ', '|', ' ', '|', '|', '_', '|', ' ', ' ', ' '),
    Array(' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', '|', ' ', ' ', ' '),
    Array(' ', '_', ' ', ' ', '_', '|', '|', '_', ' ', ' ', ' ', ' '),
    Array(' ', '_', ' ', ' ', '_', '|', ' ', '_', '|', ' ', ' ', ' '),
    Array(' ', ' ', ' ', '|', '_', '|', ' ', ' ', '|', ' ', ' ', ' '),
    Array(' ', '_', ' ', '|', '_', ' ', ' ', '_', '|', ' ', ' ', ' '),
    Array(' ', '_', ' ', '|', '_', ' ', '|', '_', '|', ' ', ' ', ' '),
    Array(' ', '_', ' ', ' ', ' ', '|', ' ', ' ', '|', ' ', ' ', ' '),
    Array(' ', '_', ' ', '|', '_', '|', '|', '_', '|', ' ', ' ', ' '),
    Array(' ', '_', ' ', '|', '_', '|', ' ', '_', '|', ' ', ' ', ' ')
  )


  def checkOcrDecoding(ocr: OcrDigit)(expectedCharArray: Array[Char]) = {
    info(s"OcrDigit(${ocr.digit}) should serialise to ${expectedCharArray.mkString}")
    ocr.asCharArray should have length 12
    ocr.asCharArray should contain theSameElementsInOrderAs expectedCharArray
  }

  forAll(0 to 9) { (i: Int) =>
    checkOcrDecoding(OcrDigit(i))(expectedValues(i))
  }
}
