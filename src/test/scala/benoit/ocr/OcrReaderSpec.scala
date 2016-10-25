package benoit.ocr

import benoit.UnitSpec

import scala.io.Source

class OcrReaderSpec extends UnitSpec {

  val accountsFile = OcrReader.ocrFile

  "OcrFile" - {

    "should split the file into discreet entries" in new OcrFile {
      override def read: Stream[Char] = """ _  _  _  _  _  _  _  _  _
                                          || || || || || || || || || |
                                          ||_||_||_||_||_||_||_||_||_|
                                          |
                                          |
                                          |  |  |  |  |  |  |  |  |  |
                                          |  |  |  |  |  |  |  |  |  |
                                          |
                                          | _  _  _  _  _  _  _  _  _
                                          | _| _| _| _| _| _| _| _| _|
                                          ||_ |_ |_ |_ |_ |_ |_ |_ |_
                                          |
                                          |""".stripMargin.toStream
    }
  }
}


trait OcrFile {
  def read: Stream[Char]
}

object OcrReader {

  def ocrFile: OcrFile = new OcrFile {
    override def read: Stream[Char] = Source.fromFile("resources/ocrcodes_one_to_ten.txt").toStream
  }
}