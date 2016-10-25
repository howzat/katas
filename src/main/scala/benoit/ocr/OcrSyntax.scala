package benoit.ocr

object OcrSyntax {

  implicit class OcrWriterOps[A](value: A) {
    def asCharArray(implicit writer: OcrWriter[A, Array[Char]]): Array[Char] = {
      writer.write(value)
    }
  }

}