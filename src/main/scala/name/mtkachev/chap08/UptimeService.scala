package name.mtkachev.chap08

import cats.Applicative
import cats.instances.list._
import cats.syntax.traverse._
import cats.syntax.functor._

import scala.concurrent.{ExecutionContext}


class UptimeService[F[_]: Applicative](client: UptimeClient[F])(implicit val ec: ExecutionContext) {
  def getTotalUptime(hostnames: List[String]): F[Int] =
    hostnames
      .traverse(h => client.getUptime(h))
      .map(_.sum)
}
