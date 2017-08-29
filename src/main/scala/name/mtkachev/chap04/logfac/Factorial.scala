package name.mtkachev.chap04.logfac

import cats.data.Writer

object Factorial {
  import cats.instances.vector._
  import cats.syntax.applicative._
  import cats.syntax.writer._

  type Logged[A] = Writer[Vector[String], A]

  def slowly[A](body: => A) =
    try body finally Thread.sleep(100)

  def factorial(n: Int): Logged[Int] = {
    val ans =
      slowly {
        if (n == 0)
          Writer(Vector(s"fact $n 1"), 1)
        else
          factorial(n - 1).flatMap { prev =>
            val cur = prev * n
            Writer(Vector(s"fact $n $cur"), cur)
          }
      }
    ans
  }

  def factorial1(n: Int): Logged[Int] = {
    for {
      ans <- if (n == 0) 1.pure[Logged]
             else slowly { factorial1(n - 1).map(_ * n) }
      _   <- Vector(s"fact $n $ans").tell
    } yield ans
  }
}
