package benoit.ocr

import benoit.UnitSpec

class OcrNumberSpec extends UnitSpec {

  "OcrNumbers" - {

    "can be values 0-9" in {

      (0 to 9) foreach {
        value =>
          OcrNumber(value+"").value shouldBe value.toString()
          OcrNumber(value+"").digits shouldBe Seq(OcrDigit(value))
      }
    }

    "can be Integer Max Int" in {

      OcrNumber("12345678").digits shouldBe Seq(
        OcrDigit(1),OcrDigit(2),OcrDigit(3),OcrDigit(4),
        OcrDigit(5),OcrDigit(6),OcrDigit(7),OcrDigit(8)
      )
    }

    "equality" in {

      OcrNumber("12345678") shouldBe OcrNumber("12345678")
    }
  }
}
