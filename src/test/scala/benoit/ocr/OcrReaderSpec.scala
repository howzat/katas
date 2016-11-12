package benoit.ocr

import java.io.{File, PrintWriter}

import benoit.UnitSpec
import benoit.ocr.DefaultOcrReaders.ocrFileReader
import benoit.ocr.DefaultOcrWriters._
import benoit.ocr.OcrSyntax.OcrWriterOps

import scala.io.Source
import scala.language.postfixOps

class OcrReaderSpec extends UnitSpec {

  private val strings = Source.fromFile("resources/ocrcodes_one_to_ten.txt").getLines().toList

  val accountNumbers = List(
    "000000000",
    "123123123",
    "111111111",
    "222222222",
    "333333333",
    "444444444",
    "555555555",
    "666666666",
    "777777777",
    "888888888",
    "999999999",
    "123456789"
  )


  forAll(accountNumbers) { accountNumber =>

    s"read the OcrNumber $accountNumber" in {

      val expectedOcrNumber = OcrNumber(accountNumber)
      val written = write(expectedOcrNumber)
      val read = ocrFileReader.read(written)

      withClue(s"Serialising $accountNumber into:\n${written.mkString("\n")}\n") {
        read shouldBe List(expectedOcrNumber)
      }
    }
  }


  "Write to file" ignore {
    s"should write to a file :|" in {

      val pw = new PrintWriter(new File("resources/ppp.txt"))
      pw.write(accountNumbers.flatMap(n => OcrNumber(n).write).mkString)
      pw.close()
    }
  }

  def write(number: OcrNumber): List[String] = {
    Source.fromString(number.write).getLines() toList
  }
}


trait OcrFile {
  def read: Stream[Char]
}

