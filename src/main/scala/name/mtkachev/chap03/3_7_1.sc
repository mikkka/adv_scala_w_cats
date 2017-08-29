import cats.Show
import cats.functor.Contravariant
import cats.instances.string._

val showString = Show[String]
val showSymbol = Contravariant[Show].contramap(showString)(
  (sym: Symbol) => s"'${sym.name}")

showSymbol.show('dave)


import cats.instances.function._
import cats.syntax.contravariant._

val div2: Int => Double = _ / 2.0
val add1: Int => Int = _ + 1
div2.contramap(add1)(2)
