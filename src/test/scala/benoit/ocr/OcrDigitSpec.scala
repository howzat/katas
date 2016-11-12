package benoit.ocr

import benoit.UnitSpec

import scala.language.postfixOps

class OcrDigitSpec extends UnitSpec {


  "OcrDigit" - {

    "fail if the number is negative" in {
      intercept[IllegalArgumentException](OcrDigit(-1))
        .getMessage shouldBe "requirement failed: [-1]! ocr digits must be positive integers between 0 and 9"
    }

    "fail if the number greater than 9" in {
      intercept[IllegalArgumentException](OcrDigit(11))
        .getMessage shouldBe "requirement failed: [11]! ocr digits must be positive integers between 0 and 9"
    }

    "digits can be serialised" in {
      forAll(0 to 9) { (i: Int) =>

        OcrDigit(i).value shouldBe i + ""
        OcrDigit(i).toString shouldBe s"OcrDigit($i)"
        OcrDigit(i).intValue shouldBe i
      }
    }
  }
}
