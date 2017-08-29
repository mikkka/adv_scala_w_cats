package name.mtkachev.chap04.postordcalc

object Calc extends App {
  import cats.data.State
  import cats.syntax.applicative._

  type CalcState[A] = State[List[Int], A]

  def evalOne(sym: String): CalcState[Int] =
    sym match {
      case "+"   => operator(_ + _)
      case "-"   => operator(_ - _)
      case "*"   => operator(_ * _)
      case "/"   => operator(_ / _)
      case digit => operand(digit.toInt)
    }

  def operator(func: (Int, Int) => Int): CalcState[Int] = State {
    case x1 :: x2 :: tail =>
      val ans = func(x1, x2)
      (ans :: tail, ans)
    case _ => sys.error("invalid stack")
  }
  
  def operand(digit: Int): CalcState[Int] = State { xs =>
    (digit :: xs, digit)
  }

  def evalAll(input: List[String]): CalcState[Int] = {
    input.foldLeft(0.pure[CalcState]) { (a, b) =>
      a flatMap (_ => evalOne(b))
    }
  }

  println(evalAll(List("1", "2", "+", "3", "*")).run(Nil).value)

  val program = for {
    _   <- evalAll(List("1", "2", "+"))
    _   <- evalAll(List("3", "4", "+"))
    ans <- evalOne("*")
  } yield ans

  println(program.run(Nil).value)
}
