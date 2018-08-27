package benoit

import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

abstract class UnitSpec extends FreeSpec with Matchers with OptionValues with Inside with Inspectors with ScalaFutures
