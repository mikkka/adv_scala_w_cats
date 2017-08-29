package name.mtkachev.chap06.monadproduct

import scala.language.higherKinds
import cats.Monad

import cats.syntax.flatMap._
import cats.syntax.functor._

object MProduct {
  def product[M[_]: Monad, A, B](fa: M[A], fb: M[B]): M[(A, B)] = {
    for {
      a <- fa
      b <- fb
    } yield (a, b)
  }
}
