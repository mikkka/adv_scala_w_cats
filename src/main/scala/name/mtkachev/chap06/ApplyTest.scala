package name.mtkachev.chap06

object ApplyValidated {
  import cats.{Monoid, Semigroup, Cartesian}
  import cats.data.Validated
  import cats.instances.string._
  import cats.syntax.apply._
  import cats.syntax.semigroup._

  type ValOr[T] = Validated[String, T]

  def apply[A: Semigroup](a: ValOr[A], b: ValOr[A]): ValOr[A] = {
    (a, b).mapN((aa, bb) => aa |+| bb)
  }

  def applyExpanded[A: Semigroup](a: ValOr[A], b: ValOr[A]): ValOr[A] = {
    val monoidForE: Monoid[String] = cats.kernel.instances.string.catsKernelStdMonoidForString
    val cartesian: Cartesian[ValOr] = Validated.catsDataApplicativeErrorForValidated(monoidForE)

    catsSyntaxTuple2Cartesian((a, b))(cartesian).mapN((aa, bb) => aa |+| bb)
  }
}

object ApplyValidated2 {
  import cats.{Monoid, Semigroup, Cartesian}
  import cats.data.Validated
  import cats.syntax.semigroup._

  def apply[E: Monoid, A: Semigroup](a: Validated[E, A], b: Validated[E, A]): Validated[E, A] = {
    Cartesian[Validated[E, ?]].product(a, b).map(x => x._1 |+| x._2)
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
