package name.mtkachev.chap03.printable


trait Printable[A] {
  def format(value: A): String

  def contramap[B](func: B => A): Printable[B] = {
    val outer = this
    new Printable[B] {
      override def format(value: B): String = outer.format(func(value))
    }
  }
}

final case class Box[A](value: A)

object PrintableInstances {
  def format[A](value: A)(implicit p: Printable[A]): String =
    p.format(value)

  implicit val stringPrintable =
    new Printable[String] {
      def format(value: String): String =
        "\"" + value + "\""
    }

  implicit val booleanPrintable =
    new Printable[Boolean] {
      def format(value: Boolean): String =
        if (value) "yes" else "no"
    }

  implicit def boxPrintable[A](implicit p: Printable[A]) =
    p.contramap[Box[A]](_.value)
}

object PrintApp extends App {
  import PrintableInstances._

  println(format("hello"))
  println(format(true))
  println(format(Box("kekus")))
}