package name.mtkachev.chap03.branching

import cats.Functor

sealed trait Tree[+A]

final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

final case class Leaf[A](value: A) extends Tree[A]

object TreeFunctor {
  implicit val branchFunctor: Functor[Branch] = new Functor[Branch] {
    override def map[A, B](fa: Branch[A])(f: (A) => B): Branch[B] =
      Branch(
        treeFunctor.map(fa.left)(f),
        treeFunctor.map(fa.right)(f)
      )
  }

  implicit val leafFunctor: Functor[Leaf] = new Functor[Leaf] {
    override def map[A, B](fa: Leaf[A])(f: (A) => B): Leaf[B] =
      Leaf(f(fa.value))
  }

  implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: (A) => B): Tree[B] = fa match {
      case b: Branch[A] => branchFunctor.map(b)(f)
      case l: Leaf[A]   => leafFunctor.map(l)(f)
    }
  }
}


object TreeApp extends App {
  import TreeFunctor._
  import cats.syntax.functor._

  println(
    Branch(
      Branch(
        Leaf(14), Leaf(88)
      ), Leaf(42)
    ).map(_ + 13)
  )
}