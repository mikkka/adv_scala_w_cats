import cats.data.Validated
import cats.data.Validated._

type ValOr[T] = Validated[List[String], T]


(Invalid(List("foo","bar","baz")): ValOr[Int]).toEither
