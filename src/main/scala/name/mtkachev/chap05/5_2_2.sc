import cats.Functor
import cats.data.OptionT
import cats.instances.either._
import cats.syntax.applicative._


type Error = String
type ErrorOr[A] = Either[Error, A]
type ErrorOptionOr[A] = OptionT[ErrorOr, A]

Functor[ErrorOr]

val result1 = 41.pure[ErrorOptionOr]
val result2 = result1.flatMap(x => (x + 1).pure[ErrorOptionOr])
val result3 = result2.map(x => x + 14)

import scala.concurrent.Future
import cats.data.EitherT

type FutureEither[A] = EitherT[Future, Error, A]
type FutureEitherOption[A] = OptionT[FutureEither, A]


import scala.concurrent.ExecutionContext.Implicits.global
import cats.instances.future._
val answer: FutureEitherOption[Int] =
  for {
    a <- 10.pure[FutureEitherOption]
    b <- 32.pure[FutureEitherOption]
  } yield a + b


answer
answer.value
answer.value.value

