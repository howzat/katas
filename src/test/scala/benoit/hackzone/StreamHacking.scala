package benoit.hackzone

import scala.io.{BufferedSource, Source}

object StreamHacking extends App {


  val results : Map[String, List[String]] = Map.empty

  private val toList: List[(String, String)] = Source.fromFile("/Users/ben/tempdata").getLines().map {
    line =>
      val arr: Array[String] = line.split(",")
      arr.head -> arr(1)
  }.toList


  def clientSize(in: (String, List[(String, String)])): (String, Int) = {
    in._1 -> in._2.map(_._2).distinct.size
  }

  toList.groupBy(_._1).map(clientSize).foreach{ case (agency, clients) =>
    println(s"|\t$agency\t$clients\t|")
  }


















}
