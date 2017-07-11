package benoit.ascats


import benoit.UnitSpec
import benoit.ascats.typeclasses.PrintersImplementations._
import benoit.ascats.typeclasses.{Person, PrinterApi, PrinterSyntax}
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

  "Functor" - {

    import cats.Functor
    import cats.instances.option._
    import cats.instances.list._

    def tuples[F[_],A](f : F[A])(implicit ftr:Functor[F]): F[(A,A)] = {
      ftr.map(f)((i:A) => (i,i))
    }


    println(tuples(Option(1)).map(_._1 * 1))
    println(tuples(Option("Fooo")))
    println(tuples(List(1)))
  }


  "Function[A,B]" - {

    type MyFunction[A] = Function[String, A]

    object MyFunction {
      def flatMap[A, B](fa: MyFunction[A])(f: A => MyFunction[B]) : MyFunction[B] = (input:String) => f(fa(input))(input)
      def pure[A](a:A) : MyFunction[A] = (input:String) => a
    }

    import MyFunction._
    val readLength: MyFunction[Int] = flatMap((i:String) => i.length)((a:Int) => pure(a) )
    println(readLength("What is the length of this string"))

  }

  "tupled" - {



    import cats.Monad
//    import cats._
//    import cats.Monoid
//    import cats.Functor
    import cats.implicits._
//    import cats.instances.option._
//    import cats.syntax.flatMap._
//    import cats.syntax.functor._
//    import cats.syntax.monoid._


    def tupled[F[_]:Monad, A, B](fa: F[A], fb:F[B]) : F[(A, B)] = {

//      fa.flatMap(theA => fb.map(theB => (theA,theB)))

            for {
              a <- fa
              b <- fb
            } yield {
              (a,b)
            }
    }
  }


  trait Random[A] {
    import Random._

    def map[B](f: A => B): Random[B] = Map(this, f)

    def flatMap[B](f : A => Random[B]) : Random[B] = FlatMap(this, f)

    def run(rng: scala.util.Random = scala.util.Random) : A = {
      def generate[B](random: Random[B]): B = {
        random match {
          case Map(s, f) => f(s.run(rng))
          case FlatMap(s, f) => f(s.run(rng)).run(rng)
          case Int => rng.nextInt()
          case Double => rng.nextDouble()
          case Pure(a) => a
        }
      }

      generate(this)
    }

  }

  object Random {
    def int: Random[Int] = Int
    def double: Random[Double] = Double
    def pure[A](a:A): Random[A] = Pure(a)
  }

  final case class Map[A,B] (source: Random[A], f: A => B) extends Random[B]
  final case class FlatMap[A,B] (source: Random[A], f: A => Random[B]) extends Random[B]
  final case class Pure[A] (a: A) extends Random[A]
  final case object Int extends Random[Int]
  final case object Double extends Random[Double]


  // Structural Recursion
  "RND" - {

    println(Random.int.run())
    println(Random.int.run())
    println(Random.int.run())
    println(Random.int.run())
    println(Random.int.run())
    println(Random.int.run())
    println(Random.int.run())
    println(Random.int.run())
    println(Random.double.run())
  }
}




































