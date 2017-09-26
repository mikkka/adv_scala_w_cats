package name.mtkachev.chap10

import cats.instances.list._

object Test extends App {
  val a: Check[List[String], Int] =
    Pure { v =>
      if(v > -2) Right(12)
      else Left(List("Must be > -2"))
    }

  val b: Check[List[String], Int] =
    Pure { v =>
      if(v < 2) Right(21)
      else Left(List("Must be < 2"))
    }

  val check = a and b

  println(check(0))
}