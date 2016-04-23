package benoit.hackzone

object StreamHacking extends App {




  def values:Stream[String] = Stream.cons("A", values)


  println((1 until 1000).map(values))

  val hello = Stream.continually("Hello World")

  println((1 until 1000).map(hello))

}
