package benoit.ascats


import benoit.UnitSpec
import benoit.ascats.typeclasses.PrintersImplementations._
import benoit.ascats.typeclasses.{Person, PrinterApi, PrinterSyntax}
import org.scalatest.DiagrammedAssertions

import scala.language.postfixOps


class TypeClassesSpec extends UnitSpec {

  val person = Person("Ben", 40, "developer")

  "Printable should format a Person data type as Name, Age and Occupation" - {

    PrinterApi.write(person) should be("Person Ben is 40 and works as a developer")
  }

  "Printable should implicitly use the infix pimeped version when in scope" - {

    import PrinterSyntax._

    person.write shouldBe "Person Ben is 40 and works as a developer"
  }
}