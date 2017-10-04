package name.mtkachev.chap10

import cats.data.Validated
import cats.data.Validated._

import cats.instances.list._

import cats.syntax.apply._

object Test extends App {
  type Errors = List[String]
  type ValOr[T] = Validated[Errors, T]

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

  val alphanumeric4 =
    Check(alphanumeric and longerThan(3))

  val emailSplitCheck: Check[Errors, String, (String, String)] = Check(
    x => x.split('@') match {
      case Array(lft, rgt) =>
        Valid((lft, rgt))
      case other =>
        Invalid(error("must contain @"))
    }
  )

  val checkEmailLeft: Check[Errors, String, String] = Check(longerThan(0))
  val checkEmailRgt : Check[Errors, String, String] = Check(longerThan(3) and contains('.'))

  val joinEmailCheck: Check[Errors, (String, String), String] = Check {case (l, r) =>
    (checkEmailLeft(l): ValOr[String],
      checkEmailRgt(r): ValOr[String]).mapN((ll, rr) => ll + "@" + rr)
  }

  val checkEmail: Check[Errors, String, String] = emailSplitCheck andThen joinEmailCheck

  final case class User(username: String, email: String)

  def createUser(username: String, email: String): Validated[Errors, User] =
    (alphanumeric4(username): ValOr[String], checkEmail(email): ValOr[String]).mapN(User)

  println(createUser("Noel", "noel@underscore.io"))
  println(createUser("", "dave@underscore@io"))
}