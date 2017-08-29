package name.mtkachev.chap01.printable

trait Printable[A] {
  def format(a: A): String
}

object Printable {
  def format[A : Printable](a: A): String =
    implicitly[Printable[A]].format(a)

  def print[A : Printable](a: A) {
    println(implicitly[Printable[A]].format(a))
  }
}
