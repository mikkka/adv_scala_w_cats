import scala.language.higherKinds
import cats.{FlatMap, Monad}

def sumSquareKek[M[_]](a: M[Int], b: M[Int])(implicit inst: Monad[M]): M[Int] = {
//def sumSquareKek[M[_] : Monad](a: M[Int], b: M[Int]): M[Int] = {
  // no synatx import -> can't
  //    a.flatMap(x => b.map(y => x * x + y * y))

  //  val inst = FlatMap[M]
  inst.flatMap(a)(x => inst.map(b)(y => x * x + y * y))
}

import cats.syntax.functor._
import cats.syntax.flatMap._

def sumSquare[M[_] : Monad](a: M[Int], b: M[Int]): M[Int] = {
  a.flatMap(x => b.map(y => x * x + y * y))
}

/*
def sumSquare4[M[_] : Monad](a: M[Int], b: M[Int]): M[Int] =
  for {
    x <- a
    y <- b
  } yield x*x + y*y

*/

//import cats.instances.option._
//import cats.instances.list._

sumSquareKek(Option(3), Option(4))(cats.instances.option.catsStdInstancesForOption)
sumSquare(Option(3), Option(4))(cats.instances.option.catsStdInstancesForOption)

/*
sumSquare4(Option(3), Option(4))

sumSquare(List(1, 2, 3), List(4, 5))
sumSquare4(List(1, 2, 3), List(4, 5))

import cats.Id


sumSquare(3: Id[Int], 4: Id[Int])
*/
