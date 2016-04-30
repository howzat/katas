package benoit.ascats


import benoit.ascats.typeclasses.PrintersImplementations._
import benoit.ascats.typeclasses.{Person, PrinterApi, PrinterSyntax}
import org.scalatest.{FlatSpec, ShouldMatchers}

import scala.language.postfixOps


class TypeClassesSpec extends FlatSpec with ShouldMatchers  {

  val person = Person("Ben", 40, "developer")

  "Printable" should "format a Person data type as Name, Age and Occupation" in {

    PrinterApi.write(person) should be("Person Ben is 40 and works as a developer")
  }

  "Printable" should "implicitly use the infix pimeped version when in scope" in {

    import PrinterSyntax._

    person.write shouldBe "Person Ben is 40 and works as a developer"
  }
}