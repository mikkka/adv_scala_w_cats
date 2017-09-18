import cats.data.Writer
import cats.instances.vector._

type Logged[A] = Writer[Vector[String], A]

def parseNumber(str: String): Logged[Option[Int]] =
  util.Try(str.toInt).toOption match {
    case Some(num) => Writer(Vector(s"Read $str"), Some(num))
    case None      => Writer(Vector(s"Failed on $str"), None)
  }


def addNumbers(a: String,  b: String, c: String): Logged[Option[Int]] = {
  import cats.data.OptionT
  val result = for {
    a <- OptionT(parseNumber(a))
    b <- OptionT(parseNumber(b))
    c <- OptionT(parseNumber(c))
  } yield a + b + c

  result.value
}



val result1 = addNumbers("1", "2", "3")
val result2 = addNumbers("1", "kek", "3")

