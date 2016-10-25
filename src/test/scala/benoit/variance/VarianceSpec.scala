package benoit.variance

import org.scalatest.{FlatSpec, Matchers}


class VarianceSpec extends FlatSpec with Matchers {


  class ImInvariant[T]
  // +T covariance, it can be used in supertype positions
  class Cell[+T](private[this] var init: T) {


    def add[UPDATE >: T](newValue:UPDATE) : Cell[UPDATE] = new Cell(newValue)

//    def set[U >: T](update: U) = init = update
  }



  class WiderTypeVarianceRelations[+T]() {
    def update[UPDATE >: T](f:UPDATE) : WiderTypeVarianceRelations[UPDATE] = ???
  }

  class MoreSpecificRelations[-T]()


  "shoulf this " should "ccndk" in {


    var cell = new Cell[String]("1230")


    val widerT = new WiderTypeVarianceRelations[String]
    val update: WiderTypeVarianceRelations[AnyRef] = widerT.update(AnyRef)



    var bar = new MoreSpecificRelations[AnyRef]()
    val bar2 : MoreSpecificRelations[String] = bar

  }
}