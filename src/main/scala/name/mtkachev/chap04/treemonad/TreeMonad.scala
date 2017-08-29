package name.mtkachev.chap04.treemonad

import cats.Monad

import scala.annotation.tailrec

class TreeMonad extends Monad[Tree] {
  import Tree._

  def pure[A](x: A): Tree[A] = leaf(x)

  def flatMap[A, B](fa: Tree[A])(f: (A) => Tree[B]): Tree[B] = fa match {
    case Leaf(a) => f(a)
    case Branch(a1, a2) =>
      branch(
        flatMap(a1)(f),
        flatMap(a2)(f)
      )
  }

//  @tailrec
  final def tailRecM[A, B](a: A)(f: (A) => Tree[Either[A, B]]): Tree[B] = {
    f(a) match {
      case Branch(l, r) =>
        branch(
          flatMap(l) {
            case Left(l)  => tailRecM(l)(f)
            case Right(l) => pure(l)
          },
          flatMap(r) {
            case Left(r)  => tailRecM(r)(f)
            case Right(r) => pure(r)
          }
        )
      case Leaf(l)      => l match {
        case Left(a)  => tailRecM(a)(f)
        case Right(b) => leaf(b)
      }
    }
  }
}
