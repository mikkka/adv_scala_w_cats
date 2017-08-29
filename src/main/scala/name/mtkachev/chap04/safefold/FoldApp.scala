package name.mtkachev.chap04.safefold

object FoldApp extends App {
  import FoldRight._

  println(
    foldRight((1 to 10000).toList, BigInt(0)) {(a, b) =>  b + a}
  )
}
