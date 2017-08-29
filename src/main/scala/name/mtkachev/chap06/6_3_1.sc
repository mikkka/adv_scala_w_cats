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