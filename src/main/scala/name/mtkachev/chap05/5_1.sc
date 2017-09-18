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


val xs: ListOption[Int] = OptionT(List(Some(41), Some(42), None))

xs.map(_ + 1)
xs.flatMap(x => OptionT(List(Option(x), Option(x))))

xs
xs.flatMap(x => OptionT(List(Option(x), None)))


for {
  x <- xs
  y <- xs
} yield (x + y)


//Monad[List].pure()

OptionT(
  xs.value.flatMap{optX =>
    optX.fold(Monad[List].pure[Option[Int]](None))(x => List(Option(x), Option(x)))
  }
)