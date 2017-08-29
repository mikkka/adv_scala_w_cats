package name.mtkachev.chap01.eq

import cats.Eq
import cats.syntax.eq._
import cats.instances.option.catsKernelStdEqForOption

object App01 extends App {
  implicit val catEqual = Eq.instance[Cat] { (cat1, cat2) =>
    import cats.instances.int._
    import cats.instances.string._

    cat1.name   == cat2.name &&
    cat1.age    == cat2.age &&
    cat1.color  == cat2.color
  }
  
  val cat1 = Cat("Garfield",   35, "orange and black")
  val cat2 = Cat("Heathcliff", 30, "orange and black")
  val optionCat1 = Option(cat1)
  val optionCat2 = Option.empty[Cat]

  println(cat1 === cat2)
  println(optionCat1 === optionCat2)
}
