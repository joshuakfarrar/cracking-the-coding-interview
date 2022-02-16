import cats.effect._
import cats.syntax.all._

object TestSuite {
  import Application.permutationB

  val testA = IO {
    permutationB("abc", "bca") == true
  }

  val testB = IO {
    permutationB("abcx", "bca") == false
  }

  val testC = IO {
    permutationB("abc", "bcaX") == false
  }

  val tests = for {
    a <- testA
    b <- testB
    c <- testC
  } yield a && b && c
}

// 1.2 Check Permutation:
// Given two strings, write a method to decide if one is a permutation of the other.

object Application extends IOApp {
  def permutationA(a: String, b: String): Boolean = a.sorted === b.sorted

  // let's do something goofy with Options
  def permutationB(a: String, b: String): Boolean = {
    def internal(c: String, d: String): Option[Boolean] = for {
      e <- c.headOption
      f <- d.headOption
      result <- if (e === f) {
        if (c.length === 1 && d.length === 1) Some(true)
        else internal(c.tail, d.tail)
      } else Some(false)
    } yield result

    if (a.length != b.length) false // exit early, not strictly necessary but a nice optimization
    else internal(a.sorted, b.sorted) match {
      case Some(result) => result
      case None => false
    }
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