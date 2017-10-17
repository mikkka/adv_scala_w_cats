package name.mtkachev.chap11

import cats.Monoid
import cats.Foldable
import cats.syntax.semigroup._
import cats.instances.map._

final case class GenericGCounter[A](counters: Map[String, A]) {
  def increment(machine: String, amount: A)(implicit m: Monoid[A]): GenericGCounter[A] = 
    GenericGCounter(
      counters + (machine -> (amount |+| counters.getOrElse(machine, m.empty)))
    )

  def get(implicit m: Monoid[A]): A = 
    Foldable[Map[String, ?]].foldMap(counters)(identity)

  def merge(that: GenericGCounter[A])
    (implicit b: BoundedSemiLattice[A]): GenericGCounter[A] = 
    GenericGCounter(counters |+| that.counters)
}
