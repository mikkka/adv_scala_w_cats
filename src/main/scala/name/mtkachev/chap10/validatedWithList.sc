
import cats.data.Validated
import cats.data.Validated._
import cats.instances.list._

import cats.syntax.cartesian._

type Errs = List[String]
type ValOr[T] = Validated[Errs, T]


val val1: ValOr[Int] = Valid(42)
val val2: ValOr[Int] = Invalid(List("foo"))
val val3: ValOr[Int] = Invalid(List("bar"))

(val1 |@| val1).map((a,b) => a + b)
(val1 |@| val3).map((a,b) => a + b)
(val2 |@| val3).map((a,b) => a + b)


import cats.syntax.apply._

(val1, val1).mapN((a,b) => a + b)
(val1, val3).mapN((a,b) => a + b)
(val2, val3).mapN((a,b) => a + b)
