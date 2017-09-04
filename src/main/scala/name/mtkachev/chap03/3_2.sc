import cats.instances.function._
import cats.syntax.functor._

val func1 = (x: Int)    => x.toDouble
val func2 = (y: Double) => (y * 2).toString
func2(func1(1))

val func3 = func1.map(func2)
func3(1)
