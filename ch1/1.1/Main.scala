import cats.effect._
import cats.syntax.all._

object TestSuite {
  import Application.unique

  val testA = IO {
    unique("abc") == true
  }

  val testB = IO {
    unique("aa") == false
  }

  val tests = for {
    a <- testA
    b <- testB
  } yield a && b
}

// 1.1 Is Unique:
// Implement an algorithm to determine if a string has all unique characters.
// What if you cannot use additional data structures?

object Application extends IOApp {
  def unique(a: String): Boolean = {
    def internal(b: String)(seen: String): Boolean = {
      b.headOption match {
        case Some(c) =>
          if (seen.contains(c)) false
          else internal(b.tail)(seen :+ c)
        case None => true
      }
    }
    internal(a)("")
  }

  def resultToYesNo(a: Boolean): String = a match {
    case true => "yes"
    case false => "no"
  }

  override def run(args: List[String]): IO[ExitCode] = for {
    result <- TestSuite.tests
    _ <- IO(println(s"did all tests pass?: ${resultToYesNo(result)}!"))
  } yield ExitCode.Success
}