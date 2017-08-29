package name.mtkachev.chap04.safefold
import cats.Eval

object FoldRight {
  def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
    foldRightEval(as, Eval.now(acc)) { (a, eb) =>
      eb.map { b =>
        fn(a, b)
      }
    }.value

  def foldRightEval[A, B](as: List[A], acc: Eval[B])
                         (fn: (A, Eval[B]) => Eval[B]): Eval[B] =
    as match {
      case head :: tail =>
        Eval.defer {
          fn(
            head,
            foldRightEval(tail, acc)(fn)
          )
        }
      case Nil =>
        acc
    }
}
