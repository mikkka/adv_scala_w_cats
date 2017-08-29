package name.mtkachev.chap01.printable

object PrintableSyntax {
  implicit class PrintOps[A](val a: A) extends AnyVal {
    def format(implicit p: Printable[A]): String = Printable.format(a)
    def print(implicit p: Printable[A]) = Printable.print(a)
  }
}
