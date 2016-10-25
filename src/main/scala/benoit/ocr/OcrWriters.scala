package benoit.ocr

trait OcrWriter[A, R] {
  def write(v: A): R
}

object DefaultOcrWriters {

  implicit val printMatrixWriter: OcrWriter[OcrDigit, Array[Char]] =
    new OcrWriter[OcrDigit, Array[Char]] {

      override def write(v: OcrDigit): Array[Char] = matrix(v.digit)

      lazy final val matrix = Array(
        " _ | ||_|   " toCharArray,
        "     |  |   " toCharArray,
        " _  _||_    " toCharArray,
        " _  _| _|   " toCharArray,
        "   |_|  |   " toCharArray,
        " _ |_  _|   " toCharArray,
        " _ |_ |_|   " toCharArray,
        " _   |  |   " toCharArray,
        " _ |_||_|   " toCharArray,
        " _ |_| _|   " toCharArray
      )
    }
}