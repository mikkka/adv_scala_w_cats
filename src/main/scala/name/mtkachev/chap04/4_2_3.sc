import scala.language.higherKinds
import cats.Monad
import cats.syntax.functor._
import cats.syntax.flatMap._


def sumSquare[M[_] : Monad](a: M[Int], b: M[Int]): M[Int] =
  a.flatMap(x => b.map(y => x*x + y*y))

def sumSquare4[M[_] : Monad](a: M[Int], b: M[Int]): M[Int] =
  for {
    x <- a
    y <- b
  } yield x*x + y*y


import cats.instances.option._
import cats.instances.list._

sumSquare(Option(3), Option(4))
sumSquare4(Option(3), Option(4))

sumSquare(List(1, 2, 3), List(4, 5))
sumSquare4(List(1, 2, 3), List(4, 5))

import cats.Id


sumSquare(3: Id[Int], 4: Id[Int])
