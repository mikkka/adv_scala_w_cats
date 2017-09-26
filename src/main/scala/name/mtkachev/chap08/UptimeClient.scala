package name.mtkachev.chap08

trait UptimeClient[F[_]] {
  def getUptime(hostname: String): F[Int]
}
