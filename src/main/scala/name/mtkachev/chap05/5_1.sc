import cats.data.OptionT

import cats.Monad
import cats.instances.list._
import cats.syntax.applicative._


type ListOption[A] = OptionT[List, A]

val result: ListOption[Int] = 42.pure[ListOption]

val a = 10.pure[ListOption]
val b = 32.pure[ListOption]

a flatMap {x => b map {y =>
  x + y
}}

for {
  x <- a
  y <- b
} yield (x + y)

