package benoit.ocr

object OcrSyntax {

  implicit class OcrWriterOps[A <: Ocr](value: A) {
    def write(implicit writer: OcrWriter[A, String]): String = {
      writer.write(value)
    }
  }
}