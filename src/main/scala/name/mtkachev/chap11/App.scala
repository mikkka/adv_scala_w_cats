package name.mtkachev.chap11

object TestApp extends App {
  val gcount1 = new GCounter(Map("foo" -> 1, "bar" -> 2))
  val gcount2 = new GCounter(Map("bar" -> 3, "ger" -> 4))

  println(gcount1.merge(gcount2))
}
