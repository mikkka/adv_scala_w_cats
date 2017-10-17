package name.mtkachev.chap11.tplcss

object TestApp extends App {
  import cats.instances.map._

  import BoundedSemiLattice._
  import KVStore._
  import GCounter._
 
  println(
    GCounter[Map, String, Set[Int]].
      merge(
        Map("foo" -> Set(1 ,2, 3),  "bar" -> Set(3, 4)), 
        Map("ger" -> Set(8, 9, 10), "bar" -> Set(3, 5, 6)))
  )


  println(
    GCounter[Map, String, Int].
      merge(
        Map("foo" -> 3,  "bar" -> 4), 
        Map("ger" -> 10, "bar" -> 6)
      )
  )

  //use syntax cyka blyat
  println(
    Map("foo" -> 3,  "bar" -> 4).
      increment("kek", 123).
      merge(Map("ger" -> 10, "bar" -> 6))
  )
}
