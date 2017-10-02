package name.mtkachev.chap10

import cats.instances.list._

object Test extends App {
  val a: Predicate[List[String], Int] =
    Pure { v =>
      if(v > -2) None
      else Some(List("Must be > -2"))
    }

  val b: Predicate[List[String], Int] =
    Pure { v =>
      if(v < 2) None
      else Some(List("Must be < 2"))
    }

  val check = a and b

  println(check(0))
}