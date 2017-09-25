import name.mtkachev.chap07.Traverse

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

val hostnames = List(
  "alpha.example.com",
  "beta.example.com",
  "gamma.demo.com"
)
def getUptime(hostname: String): Future[Int] =
  Future(hostname.length * 60)


val allUptimes: Future[List[Int]] =
  hostnames.foldLeft(Future(List.empty[Int])) {
    (accum, host) =>
      val uptime = getUptime(host)
      for {
        accum  <- accum
        uptime <- uptime
      } yield accum :+ uptime
  }
Await.result(allUptimes, 1.second)




val allUptimes1: Future[List[Int]] = Future.traverse(hostnames)(getUptime)
Await.result(allUptimes1, 1.second)


object FutureL {
  def traverse[A, B](values: List[A])(func: A => Future[B]): Future[List[B]] =
    values.foldLeft(Future(List.empty[B])) { (accum, host) =>
      val item = func(host)
      for {
        accum <- accum
        item  <- item
      } yield accum :+ item
    }

  def sequence[B](futures: List[Future[B]]): Future[List[B]] =
    traverse(futures)(identity)
}

val allUptimes2: Future[List[Int]] = FutureL.traverse(hostnames)(getUptime)
Await.result(allUptimes2, 1.second)


object FutureA {
  import cats.instances.future._
  import cats.syntax.applicative._
  import cats.syntax.cartesian._

  def traverse[A, B](values: List[A])(func: A => Future[B]): Future[List[B]] =
    values.foldLeft(List.empty[B].pure[Future]) { (accum, host) =>
      (accum |@| func(host)).map(_ :+ _)
    }

  def sequence[B](futures: List[Future[B]]): Future[List[B]] =
    traverse(futures)(identity)
}

val allUptimes3: Future[List[Int]] = FutureA.traverse(hostnames)(getUptime)
Await.result(allUptimes2, 1.second)



import cats.instances.future._
val allUptimes4: Future[List[Int]] = Traverse.traverse(hostnames)(getUptime)
Await.result(allUptimes4, 1.second)