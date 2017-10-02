package name.mtkachev.chap10

import cats.{Cartesian, Monoid}
import cats.data.Validated
import cats.data.Validated._
import cats.syntax.semigroup._

sealed trait Predicate[E, A] {
  def and(that: Predicate[E, A]): Predicate[E, A] = And(this, that)
  def or(that: Predicate[E, A]): Predicate[E, A] = Or(this, that)

  def apply(a: A)(implicit s: Monoid[E]): Validated[E, A] = {
    this match {
      case Pure(func) =>
        func(a) match {
          case Some(e) => Invalid(e)
          case None    => Valid(a)
        }
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
}

final case class And[E, A](left: Predicate[E, A], right: Predicate[E, A]) extends Predicate[E, A]

final case class Or[E, A](left: Predicate[E, A], right: Predicate[E, A]) extends Predicate[E, A]

final case class Pure[E, A](func: A => Option[E]) extends Predicate[E, A]