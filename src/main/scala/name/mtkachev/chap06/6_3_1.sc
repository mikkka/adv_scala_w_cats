import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import cats.Cartesian

import cats.instances.future._
import cats.instances.list._
import cats.instances.either._

import cats.syntax.cartesian._

val futurePair = Cartesian[Future].
  product(Future("Hello"), Future(123))

Await.result(futurePair, 1.second)


case class Cat(
                name: String,
                yearOfBirth: Int,
                favoriteFoods: List[String]
              )


val futureCat = (
  Future("Kuzya") |@|
  Future(2000) |@|
  Future(List("Chips"))
).map(Cat.apply)


Await.result(futureCat, 1.second)

Cartesian[List].product(List(1, 2), List(3, 4))

type ErrorOr[A] = Either[Vector[String], A]

Cartesian[ErrorOr].product(
  Left(Vector("Error 1")),
  Left(Vector("Error 2"))
)


import cats.Monoid
import cats.instances.int._
import cats.instances.list._
import cats.instances.string._
import cats.syntax.cartesian._

def catToTuple(cat: Cat) =
  (cat.name, cat.yearOfBirth, cat.favoriteFoods)

implicit val monoidsBldr = (Monoid[String] |@| Monoid[Int] |@| Monoid[List[String]])

implicit val tMonoid: Monoid[(String, Int, List[String])] =
  monoidsBldr.tupled

val ap = Cat.apply _
implicit val catMonoid: Monoid[Cat] =
  monoidsBldr.imap(ap)(catToTuple)

Monoid[Cat].empty

val garfield   = Cat("Garfield", 1978, List("Lasagne"))
val heathcliff = Cat("Heathcliff", 1988, List("Junk Food"))

import cats.syntax.monoid._

("Garfield", 1978, List("Lasagne")) |+| ("Heathcliff", 1988, List("Junk Food"))
garfield |+| heathcliff
