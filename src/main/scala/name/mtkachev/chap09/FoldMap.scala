package name.mtkachev.chap09

import cats.Monoid

import cats.instances.future._
import cats.instances.vector._
import cats.instances.list._

import cats.syntax.semigroup._
import cats.syntax.traverse._
import cats.syntax.foldable._

import scala.concurrent.Future

trait FoldMap {
  def foldMap[B: Monoid, A](xs: Seq[A])(f: A => B) =
    xs.foldLeft(Monoid[B].empty)(_  |+| f(_))

  def parallelFoldMap[A, B : Monoid] (values: Vector[A]) (func: A => B): Future[B] = {
    import scala.concurrent.ExecutionContext.Implicits.global
    val cpuCount = Runtime.getRuntime.availableProcessors()
    val batchSize = values.size / (cpuCount - 1)

    values
      .grouped(batchSize)
      .toList
      .traverse{xs => Future(xs.foldMap(func))}
      .map{xs => xs.combineAll}
  }
}

object FoldMap extends FoldMap
