package name.mtkachev.chap01.printable

import cats.Show
import cats.instances.int._
import cats.instances.string._
import cats.syntax.show._

object AppShow extends App {
  implicit val catShow: Show[Cat] = Show.show{cat =>
    val name  = cat.name.show
    val age   = cat.age.show
    val color = cat.color.show

    s"${name} is a ${age} year-old ${color} cat"
  }

  println(Cat("Kuzya", 11, "red").show)
}
