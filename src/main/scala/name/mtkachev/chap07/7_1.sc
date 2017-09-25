import cats.Foldable
import cats.instances.list._
import cats.instances.option._
import cats.instances.map._

val ints = List(1, 2, 3)
Foldable[List].foldLeft(ints, 0)(_ + _)

val maybeInt = Option(123)
Foldable[Option].foldLeft(maybeInt, 10)(_ * _)

type StringMap[A] = Map[String, A]
val stringMap = Map("a" -> "b", "c" -> "d")
Foldable[StringMap].foldLeft(stringMap, "nil")(_ + "," + _)

import cats.Eval
import cats.instances.stream._

val bigData = (1 to 100000).toStream

//steam is not stack safe!!!
//bigData.foldRight(0)(_ + _)
//but vector is!
(1 to 100000).toVector.foldRight(0)(_ + _)

val eval = Foldable[Stream].
  foldRight(bigData, Eval.now(0)) { (num, evalAcc) =>
    evalAcc.map(acc => acc + num)
  }
eval.value


import cats.instances.int._
Foldable[List].combineAll(List(1, 2, 3))

import cats.instances.string._
Foldable[List].foldMap(List(1, 2, 3))(_.toString)

import cats.instances.vector._
val foldCompose = Foldable[List] compose Foldable[Vector]
foldCompose.combineAll(List(Vector(1, 2, 3), Vector(4, 5, 6)))

import cats.syntax.foldable._

List(1, 2, 3).combineAll
List(1, 2, 3).foldMap(_.toString)
