import cats.instances.list._
import cats.syntax.traverse._
import cats.instances.either._

type ErrorOr[T] = Either[String, T]

val eithers1: List[Either[String, String]] = List(
  Right("Wow!"),
  Right("Such cool!")
)
eithers1.sequenceU
//eithers1.sequence

val eithers2: List[ErrorOr[String]] = List(
  Right("Wow!"),
  Right("Such cool!")
)
eithers2.sequenceU
//eithers2.sequence
