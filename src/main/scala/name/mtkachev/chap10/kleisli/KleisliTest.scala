package name.mtkachev.chap10.kleisli

import cats.data.Kleisli
import cats.instances.either._
import cats.instances.list._
import cats.syntax.apply._

object KleisliTest extends App {
  type Errors = List[String]
  type Result[A] = Either[Errors, A]

  // i.e. A => Result[B]
  type Check[A, B] = Kleisli[Result, A, B]

  def check[A, B](f: A => Result[B]): Check[A, B] = Kleisli(f)

  def check[A](p: Predicate[Errors, A]): Check[A, A] = Kleisli[Result, A, A](p.run)

  def error(s: String): List[String] =
    List(s)

  def longerThan(n: Int): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must be longer than $n characters"),
      str => str.size > n)

  val alphanumeric: Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must be all alphanumeric characters"),
      str => str.forall(_.isLetterOrDigit))

  def contains(char: Char): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must contain the character $char"),
      str => str.contains(char))

  def containsOnce(char: Char): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must contain the character $char only once"),
      str => str.filter(c => c == char).size == 1)

  val alphanumeric4: Check[String, String] =
    check((alphanumeric and longerThan(3)).run)

  val emailSplitCheck: Check[String, (String, String)] = check { x =>
    x.split('@') match {
      case Array(lft, rgt) =>
        Right((lft, rgt))
      case other =>
        Left(error("must contain @"))
    }
  }

  val checkEmailLeft: Check[String, String] = check(longerThan(0))
  val checkEmailRgt : Check[String, String] = check(longerThan(3) and contains('.'))

  val joinEmailCheck: Check[(String, String), String] = check {case (l, r) =>
    (checkEmailLeft(l): Result[String],
      checkEmailRgt(r): Result[String]).mapN((ll, rr) => ll + "@" + rr)
  }

  val checkEmail: Check[String, String] = emailSplitCheck andThen joinEmailCheck

  final case class User(username: String, email: String)

  def createUser(username: String, email: String): Either[Errors, User] =
    (alphanumeric4(username): Result[String], checkEmail(email): Result[String]).mapN(User)

  println(createUser("Noel", "noel@underscore.io"))
  println(createUser("", "dave@underscore@io"))
}
