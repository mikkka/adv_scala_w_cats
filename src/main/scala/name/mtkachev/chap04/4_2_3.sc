import scala.language.higherKinds
import cats.{FlatMap, Monad}

def sumSquareInstExplicit[M[_]](a: M[Int], b: M[Int])(inst: Monad[M]): M[Int] = {
  inst.flatMap(a)(x => inst.map(b)(y => x * x + y * y))
}

def sumSquareInstExplicitSyntax[M[_]](a: M[Int], b: M[Int])(inst: Monad[M]): M[Int] = {
  FlatMap.ops.toAllFlatMapOps(a)(inst).flatMap {x =>
    FlatMap.ops.toAllFlatMapOps(b)(inst).map {y =>
      x * x + y * y
    }
  }
}

def sumSquareInst[M[_] : Monad](a: M[Int], b: M[Int]): M[Int] = {
  val inst = FlatMap[M]
  inst.flatMap(a)(x => inst.map(b)(y => x * x + y * y))
}

import cats.syntax.functor._
import cats.syntax.flatMap._

def sumSquareSyntaxEx[M[_] : Monad](a: M[Int], b: M[Int]): M[Int] = {
  a.flatMap(x => b.map(y => x * x + y * y))
}

def sumSquareSyntax[M[_] : Monad](a: M[Int], b: M[Int]): M[Int] = {
  a.flatMap(x => b.map(y => x * x + y * y))
}

def sumSquareSyntax4[M[_] : Monad](a: M[Int], b: M[Int]): M[Int] =
  for {
    x <- a
    y <- b
  } yield x*x + y*y



// explicit calls
sumSquareInstExplicit(Option(3), Option(4))(cats.instances.option.catsStdInstancesForOption)
sumSquareInstExplicitSyntax(Option(3), Option(4))(cats.instances.option.catsStdInstancesForOption)
sumSquareInst(Option(3), Option(4))(cats.instances.option.catsStdInstancesForOption)
sumSquareSyntax(Option(3), Option(4))(cats.instances.option.catsStdInstancesForOption)
sumSquareSyntax4(Option(3), Option(4))(cats.instances.option.catsStdInstancesForOption)

import cats.instances.option._

// implicit calls
sumSquareInst(Option(3), Option(4))
sumSquareSyntax(Option(3), Option(4))
sumSquareSyntax4(Option(3), Option(4))


val xs = List(1, 2, 3)
val ys = List(4, 5)

// explicit calls
sumSquareInstExplicit(xs, ys)(cats.instances.list.catsStdInstancesForList)
sumSquareInstExplicitSyntax(xs, ys)(cats.instances.list.catsStdInstancesForList)
sumSquareInst(xs, ys)(cats.instances.list.catsStdInstancesForList)
sumSquareSyntax(xs, ys)(cats.instances.list.catsStdInstancesForList)
sumSquareSyntax4(xs, ys)(cats.instances.list.catsStdInstancesForList)

import cats.instances.list._
// implicit calls
sumSquareInst(xs, ys)
sumSquareSyntax(xs, ys)
sumSquareSyntax4(xs, ys)


import cats.Id
// explicit calls
sumSquareInstExplicit(3: Id[Int], 4: Id[Int])(cats.catsInstancesForId)
sumSquareInstExplicitSyntax(3: Id[Int], 4: Id[Int])(cats.catsInstancesForId)
sumSquareInst(3: Id[Int], 4: Id[Int])(cats.catsInstancesForId)
sumSquareSyntax(3: Id[Int], 4: Id[Int])(cats.catsInstancesForId)
sumSquareSyntax4(3: Id[Int], 4: Id[Int])(cats.catsInstancesForId)

sumSquareInst(3: Id[Int], 4: Id[Int])
sumSquareSyntax(3: Id[Int], 4: Id[Int])
sumSquareSyntax4(3: Id[Int], 4: Id[Int])

