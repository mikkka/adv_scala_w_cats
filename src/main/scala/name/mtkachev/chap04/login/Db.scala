package name.mtkachev.chap04.login

import cats.data.Reader

case class Db(usernames: Map[Int, String], passwords: Map[String, String])

object Db {
  type DbReader[T] = Reader[Db,T]
}