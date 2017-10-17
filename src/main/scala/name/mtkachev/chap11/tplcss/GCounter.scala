package name.mtkachev.chap11.tplcss

import scala.language.higherKinds

import cats.Monoid
import cats.Foldable

trait BoundedSemiLattice[A] extends Monoid[A] {
  def combine(a1: A, a2: A): A
  def empty: A
}

object BoundedSemiLattice {
  class SetBoundedSemiLattice[A] extends BoundedSemiLattice[Set[A]] {
    override def combine(a1: Set[A], a2: Set[A]): Set[A] = a1.union(a2)
    override def empty: Set[A] = Set.empty
  }

  implicit object intBoundedSemiLattice extends BoundedSemiLattice[Int] {
    override def combine(a1: Int, a2: Int): Int = a1.max(a2)
    override def empty: Int = 0
  }

  implicit def setBoundedSemiLattice[A]: BoundedSemiLattice[Set[A]] = new SetBoundedSemiLattice[A]()
}

trait GCounter[F[_, _], K, V] {
  def increment(f: F[K, V])(k: K, v: V): F[K, V]
  def total(f: F[K, V]): V
  def merge(f1: F[K, V], f2: F[K, V]): F[K, V]
}

trait KVStore[F[_, _]] {
  def plus[K, V](f: F[K, V])(key: K, value: V): F[K, V]
  def get[K, V](f: F[K, V])(key: K): Option[V]
  def getOrElse[K, V](f: F[K, V])(key: K, default : => V): V = 
    get(f)(key).getOrElse(default)
}

object KVStore {
  implicit class KVStoreOps[F[_, _], K, V](f: F[K, V]) {
    def plus(key: K, value: V)(implicit kv: KVStore[F]): F[K, V] = 
      kv.plus(f)(key, value)

    def get(key: K)(implicit kv: KVStore[F]): Option[V] = 
      kv.get(f)(key)

    def getOrElse(key: K, default : => V)(implicit kv: KVStore[F]): V = 
      kv.getOrElse(f)(key, default)
  }

  implicit object mapKeyValueStore extends KVStore[Map] {
    def plus[K, V](f: Map[K, V])(key: K, value: V): Map[K, V] = 
      f + (key -> value)
    
    def get[K, V](f: Map[K, V])(key: K): Option[V] =
      f.get(key)
  }
}

object GCounter {
  def apply[F[_, _], K, V](implicit g: GCounter[F, K, V]) = g

  implicit def kvInstance[F[_, _], K, V](
    implicit 
    kv : KVStore[F], 
    mkv: Monoid[F[K, V]],
    fkv: Foldable[F[K, ?]],
    semiLat: BoundedSemiLattice[V]
  ): GCounter[F, K, V] = new GCounter[F, K, V] {
    import KVStore._
    import cats.syntax.monoid._

    def increment(f: F[K, V])(k: K, v: V): F[K, V] = 
      f.plus(k, f.getOrElse(k, semiLat.empty) |+| v)

    def total(f: F[K, V]): V = 
      fkv.foldMap(f)(identity(_))

    def merge(f1: F[K, V], f2: F[K, V]): F[K, V] = 
      f1 |+| f2
  }

  implicit class GCounterOps[F[_, _], K, V](f: F[K, V]) {
    def increment(k: K, v: V)(implicit g: GCounter[F, K, V]): F[K, V] = 
      g.increment(f)(k, v)

    def total(implicit g: GCounter[F, K, V]): V = 
      g.total(f)

    def merge(f2: F[K, V])(implicit g: GCounter[F, K, V]): F[K, V] = 
      g.merge(f, f2)
  }
}
