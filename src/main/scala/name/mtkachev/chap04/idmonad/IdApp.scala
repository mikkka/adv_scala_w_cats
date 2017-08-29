package name.mtkachev.chap04.idmonad

import IdFun._
object IdApp extends App {
  println(
    map(flatMap(pure(2))(_ * 2))(_ + 3)
  )
}
