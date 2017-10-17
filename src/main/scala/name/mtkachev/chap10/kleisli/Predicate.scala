package name.mtkachev.chap10.kleisli

import cats.{Cartesian, Semigroup}
import cats.data.Validated
import cats.data.Validated._
import cats.syntax.semigroup._

sealed trait Predicate[E, A] {
  import Predicate._

  def and(that: Predicate[E, A]): Predicate[E, A] = And(this, that)
  def or(that: Predicate[E, A]): Predicate[E, A] = Or(this, that)

  def apply(a: A)(implicit s: Semigroup[E]): Validated[E, A] = {
    this match {
      case Pure(func) =>
        func(a)

      case And(lft, rgt) =>
        Cartesian[Validated[E, ?]].product(lft(a), rgt(a)).map(_ => a)

      case Or(lft, rgt) =>
        lft(a) match {
          case Valid(_) => Valid(a)
          case Invalid(e1) =>
            rgt(a) match {
              case Valid(_) => Valid(a)
              case Invalid(e2) => Invalid(e1 |+| e2)
            }
        }
    }
  }

  def run(implicit s: Semigroup[E]): A => Either[E, A] = (a: A) => this(a).toEither
}

object Predicate {
  final case class And[E, A](left: Predicate[E, A], right: Predicate[E, A]) extends Predicate[E, A]

  final case class Or[E, A](left: Predicate[E, A], right: Predicate[E, A]) extends Predicate[E, A]

  final case class Pure[E, A](func: A => Validated[E, A]) extends Predicate[E, A]

  def lift[E, A](error: E, func: A => Boolean): Predicate[E, A] =
    Pure(a => if(func(a)) Valid(a) else Invalid(error))
}
