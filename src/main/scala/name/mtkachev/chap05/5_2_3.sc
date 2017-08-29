import cats.data.OptionT
import cats.instances.either._
import cats.syntax.option._
import cats.syntax.applicative._

type ErrorOr[A]       = Either[String, A]
type ErrorOrOption[A] = OptionT[ErrorOr, A]

// Create using pure:
val stack1 = 123.pure[ErrorOrOption]

// Create using apply:
val stack2 = OptionT[ErrorOr, Int] (
  Right(123.some)
)

// Create using apply:
val stack3 = OptionT[ErrorOr, Int] (
  Left("cyka")
)


stack1.value
stack2.value
stack3.value


import cats.instances.vector._
import cats.data.{Writer, EitherT, OptionT}

type Logged[A] = Writer[Vector[String], A]
type LoggedFallable[A] = EitherT[Logged, String, A]
type LoggedFallableOption[A] = OptionT[LoggedFallable, A]

val packed = 123.pure[LoggedFallableOption]
val partiallyPacked = packed.value
val completelyUnpacked = partiallyPacked.value

completelyUnpacked.run._2.right.get.get


