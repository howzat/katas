package benoit.hackzone

import org.scalacheck._
import org.scalacheck.Prop._
import org.scalacheck.Test.Parameters
import org.scalatest.prop
import org.scalacheck.Prop.forAll
import org.scalacheck.Test._
import org.scalacheck.Test.Parameters.Default


object RGxx extends Properties("Password Validation") {

  def minimumLength(candidatePassword: String): Boolean = candidatePassword.length() >= 8
  def containRepeatedChars(candidatePassword: String): Boolean = """(\w)\1{2,}""".r.pattern.matcher(candidatePassword).matches()

  val projects=Seq("application", "admin", "customer", "catalogue", "pvastub", "provisostub", "worldpaystub")
  for (project <- projects) yield {
    println(s"""set dcj$project to (launch session "Default Session")""")
    println(s"""tell dcj$project""")
    println(s"""write text "export DCJ_HOME=" & DCJ_HOME""")
    println("""write text "cd $DCJ_HOME""" + s"""/$project"""")
    println("""write text "./run.sh"""")
    println("""end tell""")
    println("""""")
  }
}
