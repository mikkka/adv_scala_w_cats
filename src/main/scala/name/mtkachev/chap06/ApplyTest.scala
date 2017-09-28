package name.mtkachev.chap06

object ApplyValidated {
  import cats.Semigroup
  import cats.data.Validated
  import cats.instances.string._
  import cats.syntax.apply._
  import cats.syntax.semigroup._

  type ValOr[T] = Validated[String, T]

  def apply[A: Semigroup](a: ValOr[A], b: ValOr[A]): ValOr[A] = {
    (a, b).mapN((aa, bb) => aa |+| bb)
  }
}

object ApplyEither {
  import cats.Semigroup
  import cats.instances.either._
  import cats.syntax.apply._
  import cats.syntax.semigroup._

  type EitOr[T] = Either[String, T]

  def apply[A: Semigroup](a: EitOr[A], b: EitOr[A]): EitOr[A] = {
    (a,  b).mapN((aa, bb) => aa |+| bb)
  }
}

object ApplyOption {
  import cats.Semigroup
  import cats.instances.option._
  import cats.syntax.apply._

  def apply[A : Semigroup](a: Option[A], b: Option[A]): Option[A] = {
    (a, b).mapN((aa, bb) => aa)
  }
}
