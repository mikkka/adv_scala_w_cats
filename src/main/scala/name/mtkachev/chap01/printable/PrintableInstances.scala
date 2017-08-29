package name.mtkachev.chap01.printable

object PrintableInstances {
  implicit val intPrintable = new Printable[Int] {
    override def format(a: Int): String = a.toString
  }
  implicit val stringPrintable = new Printable[String] {
    override def format(a: String): String = a
  }
}
