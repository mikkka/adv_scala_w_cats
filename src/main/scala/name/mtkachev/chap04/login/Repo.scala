package name.mtkachev.chap04.login

import cats.data.Reader
import name.mtkachev.chap04.login.Db.DbReader

object Repo {
  def findUsername(userId: Int): DbReader[Option[String]] = Reader {db =>
    db.usernames.get(userId)
  }

  def checkPassword(username: String, password: String): DbReader[Boolean] = Reader {db =>
    db.passwords.get(username).contains(password)
  }
}
