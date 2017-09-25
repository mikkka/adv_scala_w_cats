package name.mtkachev.chap07

import cats.Applicative
import cats.syntax.applicative._
import cats.syntax.cartesian._

object Traverse {
  def traverse[F[_] : Applicative, A, B](values: List[A])(func: A => F[B]): F[List[B]] =
    values.foldLeft(List.empty[B].pure[F]) { (accum, host) =>
      (accum |@| func(host)).map(_ :+ _)
    }

  def sequence[F[_] : Applicative, B](fs: List[F[B]]): F[List[B]] =
    traverse(fs)(identity)
}
