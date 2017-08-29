package name.mtkachev.chap02.boolsets

trait Semigroup[A] {
  def combine(x: A, y: A): A
}

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}
object Monoid {
  def apply[A](implicit monoid: Monoid[A]): Monoid[A] = monoid
}