package name.mtkachev.chap09

import name.mtkachev.chap09.FoldMap.parallelFoldMap

import scala.concurrent.Await
import scala.concurrent.duration._

import cats.instances.int._

object Test extends App {
  println(Await.result(parallelFoldMap((1 to 1000000).toVector)(identity), 1.second))
}
