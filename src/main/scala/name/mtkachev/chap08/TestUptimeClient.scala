package name.mtkachev.chap08

import cats.Id

class TestUptimeClient(hosts: Map[String, Int]) extends UptimeClient[Id] {
  def getUptime(hostname: String) = hosts.getOrElse(hostname, 0)
}
