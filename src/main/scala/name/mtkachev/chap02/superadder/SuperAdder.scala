package name.mtkachev.chap02.superadder
import cats.Monoid

class SuperAdder {
  import cats.syntax.semigroup._
  def add[T : Monoid](items: List[T]): T =
    items.foldLeft(Monoid[T].empty) (_ |+| _)
}

sealed case class Order(totalCost: Double, quantity: Double)

object AddApp extends App {
  import cats.instances.int._
  import cats.instances.option._

  implicit val orderMonoid = new Monoid[Order] {
    override def empty: Order = Order(0, 0)

    override def combine(x: Order, y: Order): Order =
      Order(x.totalCost + y.totalCost, x.quantity + y.quantity)
  }

  println(new SuperAdder().add(List(1,2,3)))
  println(new SuperAdder().add(List(Option(1), Option(2), Option(3))))
  println(new SuperAdder().add(List(Order(1,2), Order(3,4))))
}
