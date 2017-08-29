import cats.Cartesian
import cats.instances.option._


Cartesian[Option].product(Some(123), Some("abc"))
Cartesian[Option].product(Some(123), None)
Cartesian.tuple3(Option(1), Option(2), Option(3))
Cartesian.tuple3(Option(1), Option(2), Option.empty[Int])

Cartesian.map3(
  Option(1),
  Option(2),
  Option(3)
)(_ * _ - _)

Cartesian.map3(
  Option(1),
  Option(2),
  Option.empty[Int]
)(_ * _ * _)


import cats.syntax.cartesian._

Option(1) |@| Option(2)


case class Cat(name: String, born: Int, color: String)

(
  Option("Kuzya") |@|
  Option(2000) |@|
  Option("Orange and black")
).map(Cat.apply)
