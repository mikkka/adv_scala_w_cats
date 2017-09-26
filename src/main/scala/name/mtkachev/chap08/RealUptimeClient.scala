package name.mtkachev.chap08

import scala.concurrent.Future

trait RealUptimeClient extends UptimeClient[Future] {
  def getUptime(hostname: String): Future[Int]
}
