package name.mtkachev.chap02.boolsets

object SetMonoids {
  implicit def setUnionMonoid[A]: Monoid[Set[A]] =
    new Monoid[Set[A]] {
      def combine(a: Set[A], b: Set[A]) = a ++ b

      def empty = Set.empty
    }

  implicit def setIntersectionSemigroup[A]: Semigroup[Set[A]] =
    new Semigroup[Set[A]] {
      def combine(a: Set[A], b: Set[A]) = a intersect b
    }
}
