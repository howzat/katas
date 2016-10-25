package benoit.ocr

import benoit.UnitSpec


class OcrWriterSpec extends UnitSpec {

  type Entry = Array[Array[String]]
//
//  def toOcrEntry(digits: Int*): Entry = {
//    // 4 lines to print in each entry
//    val lines: Entry = Array(Array[String](), Array[String](), Array[String](), Array[String]())
//
//    digits.foreach { digitToEncode =>
//      val encoded = OcrValue(digitToEncode)
//      lines(0) = lines(0) :+ encoded.contentByLine(0)
//      lines(1) = lines(1) :+ encoded.contentByLine(1)
//      lines(2) = lines(2) :+ encoded.contentByLine(2)
//      lines(3) = lines(3) :+ encoded.contentByLine(3)
//    }
//
//    lines
//  }
//
//  def writeEntry(entry: Entry): String = {
//    val printLines = entry map (_ mkString("", "", "\n"))
//    (printLines mkString) dropRight 1 // remove trailing newline
//  }
//
//  "An ocr writer" - {
//
//    "should encode a single 0 to OcrNumber" in {
//      OcrValue(0) shouldBe " _ \n" + "| |\n" + "|_|\n" + "   "
//    }
//
//
//    "should encode a single OcrNumber 0" in {
//
//      val expectedEncoding = Array(Array(" _ "), Array("| |"), Array("|_|"), Array("   "))
//
//      val encoded = toOcrEntry(0)
//      encoded shouldBe expectedEncoding
//      writeEntry(encoded) shouldBe " _ \n" + "| |\n" + "|_|\n" + "   "
//    }
//
//    "should encode a 1 digit" in {
//
//      val expectedEncoding = Array(Array("   "), Array("  |"), Array("  |"), Array("   "))
//
//      val encoded = toOcrEntry(1)
//      encoded shouldBe expectedEncoding
//      writeEntry(encoded) shouldBe "   \n" + "  |\n" + "  |\n" + "   "
//    }
//
//    "should encode 2 '0' digits" in {
//
//      val expectedEncoding = Array(Array(" _ ", " _ "), Array("| |", "| |"), Array("|_|", "|_|"), Array("   ", "   "))
//
//      val encoded = toOcrEntry(0, 0)
//      encoded shouldBe expectedEncoding
//      writeEntry(encoded) shouldBe " _  _ \n" + "| || |\n" + "|_||_|\n" + "      "
//    }
//
//
//
//    "should encode a sequence of 2 1's" in {
//
//      val expectedEncoding = Array(Array("   ", "   "), Array("  |", "  |"), Array("  |", "  |"), Array("   ", "   "))
//
//      val encoded = toOcrEntry(1, 1)
//      encoded shouldBe expectedEncoding
//      writeEntry(encoded) shouldBe "      \n" + "  |  |\n" + "  |  |\n" + "      "
//    }
//  }
}

