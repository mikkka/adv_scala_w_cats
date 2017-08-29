package name.mtkachev.chap04.logfac

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import Factorial._

object Runner extends App {
  val facs = Await.result(
    Future.sequence(
      Vector(
        Future(factorial1(3)),
        Future(factorial1(3))
      )),
    5.seconds)

  println(facs)
}
