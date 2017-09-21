package name.mtkachev.chap06.formvalidation

import scala.util.Try

import cats.data.Validated
import cats.syntax.either._
import cats.syntax.cartesian._
import cats.instances.list._

object Form {
  def getValue(src: Map[String, String], valname: String): Either[String, String] =
    Either.fromOption(src.get(valname), s"no val [$valname]")

  def parseInt(str: String): Either[String, Int] =
    Either.fromTry {
      Try {
        str.toInt
      }
    }.leftMap(_ => s"bad number '$str'")

  def nonBlank(str: String): Either[String, String] =
    if (str == null || str.isEmpty) Left("null or blank") else Right(str)

  def nonNegative(num: Int): Either[String, Int] =
    if (num < 0) Left(num.toString) else Right(num)

  def readName(vals: Map[String, String]): Either[List[String], String] =
    (for {
      nameStr <- getValue(vals, "name")
      name <- nonBlank(nameStr)
    } yield name).leftMap(x => List(x))

  def readAge(vals: Map[String, String]): Either[List[String], Int] =
    (for {
      ageStr <- getValue(vals, "age")
      age <- parseInt(ageStr)
      posAge <- nonNegative(age)
    } yield posAge).leftMap(x => List(x))


  def readUser(vals: Map[String, String]): Validated[List[String], User] = {
    val tpld = (Validated.fromEither(readName(vals)) |@| Validated.fromEither(readAge(vals))).tupled
    val userF = (User.apply _).tupled
    tpld map userF
  }
}

object FormApp extends App {
  import Form._

  println(readUser(Map("name" -> "Kekus", "age" -> "42")))
  println(readUser(Map.empty))
  println(readUser(Map("name" -> "", "age" -> "")))
  println(readUser(Map("name" -> "", "age" -> "-42")))
}
