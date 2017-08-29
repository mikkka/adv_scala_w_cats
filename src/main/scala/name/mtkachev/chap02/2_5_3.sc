import cats.{Monoid, Semigroup}
import cats.instances.int._
import cats.instances.option._
import cats.syntax.semigroup._

val a = Option(22)
val b = Option(20)
val c = Option.empty[Int]

Monoid[Option[Int]].combine(a, b)
Monoid[Option[Int]].combine(a, c)


a |+| b
a |+| c

3 |+| 4
catsSyntaxSemigroup(3)(catsKernelStdGroupForInt) |+| 4
val semiImp: Semigroup[Int] = catsKernelStdGroupForInt

semiImp.combine(1, 2)
