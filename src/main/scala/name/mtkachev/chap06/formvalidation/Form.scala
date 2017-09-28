package name.mtkachev.chap06.formvalidation

import scala.util.Try

import cats.data.Validated
import cats.instances.list._

import cats.syntax.either._
import cats.syntax.apply._

object Form {
  type ErrorOr[T] = Either[String, T]
  type ErrorsOr[T] = Validated[List[String], T]

  def getValue(name: String)(src: Map[String, String]): ErrorOr[String] =
    Either.fromOption(src.get(name), s"no val [$name]")

  def parseInt(name: String)(str: String): Either[String, Int] =
    Either.fromTry { Try {str.toInt} }.leftMap(_ => s"bad number '$str'")

  def nonBlank(name: String)(str: String): ErrorOr[String] =
    Right(str).ensure(s"$name non empty")(_.nonEmpty)

  def nonNegative(name: String)(num: Int): ErrorOr[Int] =
    Right(num).ensure(s"$name non negative")(_ > 0)

  def readName(vals: Map[String, String]): ErrorsOr[String] =
    getValue("name")(vals).
      flatMap(nonBlank("name")).
      leftMap(List(_)).toValidated

  def readAge(vals: Map[String, String]): ErrorsOr[Int] =
    getValue("age")(vals).
      flatMap(parseInt("age")).
      flatMap(nonNegative("age")).
      leftMap(List(_)).toValidated


  def readUser(vals: Map[String, String]): ErrorsOr[User] = {
    (readName(vals), readAge(vals)).mapN(User.apply)
  }

}

object FormApp extends App {
  import Form._

  println(readUser(Map("name" -> "Kekus", "age" -> "42")))
  println(readUser(Map.empty))
  println(readUser(Map("name" -> "", "age" -> "")))
  println(readUser(Map("name" -> "", "age" -> "-42")))
}
