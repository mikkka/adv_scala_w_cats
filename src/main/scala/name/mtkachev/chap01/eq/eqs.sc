import cats.Eq

import cats.instances.int._
import cats.instances.string._
import cats.instances.option._

import cats.syntax.eq._
import cats.syntax.option._

//use scala implicitly
implicitly[Eq[Int]]

//use Eq.apply
Eq[Int]

Eq[String]

Eq[Int].eqv(123, 123)
Eq[String].eqv("foo", "bar")

42 === 42

41.some === 42.some

"foo".some === "foo".some
