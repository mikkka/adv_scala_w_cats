package name.mtkachev.chap05.autobots

import cats.data.EitherT
import cats.instances.future._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import scala.concurrent._
import scala.concurrent.duration._

object Autobots {
  type Response[A] = EitherT[Future, String, A]

  val powerLevels = Map(
    "Jazz"      -> 6,
    "Bumblebee" -> 8,
    "Hot Rod"   -> 10
  )

  def getPowerLevel(autobot: String): Response[Int] =
    powerLevels.get(autobot) match {
      case Some(level)  => EitherT.right(Future.successful(level))
      case None         => EitherT.left(Future.successful(s"$autobot yok"))
    }

  def canSpecialMove(ally1: String, ally2: String): Response[Boolean] = {
    for {
      p1 <- getPowerLevel(ally1)
      p2 <- getPowerLevel(ally2)
    } yield {
      p1 + p2 > 15
    }
  }

  def tacticalReport(ally1: String, ally2: String): String = {
    val fres = Await.result(canSpecialMove(ally1, ally2).value, 1 second)
    fres match {
      case Right(true)  => s"$ally1 and $ally2 are ready"
      case Right(false) => s"$ally1 and $ally2 can't do it now"
      case Left(msg)    => msg
    }
  }
}

object AutobotApp extends App {
  import Autobots._

  println(tacticalReport("Jazz", "Bumblebee"))
  println(tacticalReport("Bumblebee", "Hot Rod"))
  println(tacticalReport("Jazz", "Ironhide"))
}
