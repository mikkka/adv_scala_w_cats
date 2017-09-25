

import cats.instances.vector._
import name.mtkachev.chap07.Traverse

Traverse.sequence(List(Vector(1, 2), Vector(3, 4)))
Traverse.sequence(List(Vector(1, 2), Vector(3, 4), Vector(5, 6)))

import cats.instances.option._
def process(inputs: List[Int]) =
  Traverse.traverse(inputs)(n => if(n % 2 == 0) Some(n) else None)

process(List(2, 4, 6))
process(List(1, 2, 3))


import cats.data.Validated
import cats.instances.list._

type ErrorsOr[A] = Validated[List[String], A]

def processV(inputs: List[Int]): ErrorsOr[List[Int]] =
  Traverse.traverse(inputs) { n =>
    if(n % 2 == 0) {
      Validated.valid(n)
    } else {
      Validated.invalid(List(s"$n is not even"))
    }
  }

processV(List(2, 4, 6))
processV(List(1, 2, 3))
