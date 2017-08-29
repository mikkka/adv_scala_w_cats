package name.mtkachev.chap04.login

import name.mtkachev.chap04.login.Db.DbReader
import Repo._

object LoginApp extends App {
  import cats.syntax.applicative._

  def checkLogin(userId: Int, password: String): DbReader[Boolean] =
    for {
      username <- findUsername(userId)
      result   <- username.map {uname =>
        checkPassword(uname, password)
      }.getOrElse {
        false.pure[DbReader]
      }
    } yield result

  val db = Db(
    Map(
      1 -> "dade",
      2 -> "kate",
      3 -> "margo"
    ),
    Map(
      "dade" -> "zerocool",
      "kate" -> "acidburn",
      "margo" -> "secret"
    )
  )

  println(checkLogin(1, "zerocool").run(db))
  println(checkLogin(4, "davinci").run(db))
}
