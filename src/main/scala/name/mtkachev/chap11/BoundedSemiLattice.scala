package name.mtkachev.chap11
import cats.Monoid

trait BoundedSemiLattice[A] extends Monoid[A] {
  def combine(a1: A, a2: A): A
  def empty: A
}

class IntBoundedSemiLattice extends BoundedSemiLattice[Int] {
  override def combine(a1: Int, a2: Int): Int = a1.max(a2)
  override def empty: Int = 0
}

class SetBoundedSemiLattice[A] extends BoundedSemiLattice[Set[A]] {
  override def combine(a1: Set[A], a2: Set[A]): Set[A] = a1.union(a2)
  override def empty: Set[A] = Set.empty
}

object BoundedSemiLatticeInst {
  implicit def intBoundedSemiLattice: BoundedSemiLattice[Int] = 
    new IntBoundedSemiLattice()

  implicit def setBoundedSemiLattice[A]: BoundedSemiLattice[Set[A]] = new SetBoundedSemiLattice[A]()
}
