package name.mtkachev.chap10

import cats.Semigroup
import cats.syntax.semigroup._

sealed trait Check[E, A] {
  def apply(a: A)(implicit s: Semigroup[E]): Either[E, A] = this match {
    case Pure(func) => func(a)
    case And(lft, rgt) =>
      (lft(a), rgt(a)) match {
        case (Left(e1), Left(e2))   => Left(e1 |+| e2)
        case (Left(e),  Right(_))   => Left(e)
        case (Right(_),  Left(e))   => Left(e)
        case (Right(_), Right(_))   => Right(a)
      }
    case Or(lft, rgt) =>
      (lft(a), rgt(a)) match {
        case (Left(e1), Left(e2))   => Left(e1 |+| e2)
        case (Left(e),  Right(_))   => Right(a)
        case (Right(_),  Left(e))   => Right(a)
        case (Right(_), Right(_))   => Right(a)
      }
  }

  def and(that: Check[E, A]): Check[E, A] = And(this, that)
  def or(that: Check[E, A]): Check[E, A] = Or(this, that)
}

case class And[E, A](lft: Check[E, A], rgt: Check[E, A]) extends Check[E, A]
case class Or[E, A](lft: Check[E, A], rgt: Check[E, A]) extends Check[E, A]
case class Pure[E, A](func: A => Either[E, A]) extends Check[E, A]
