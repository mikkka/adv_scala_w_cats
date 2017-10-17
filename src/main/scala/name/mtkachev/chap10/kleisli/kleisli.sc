import cats.data.Kleisli
import cats.instances.list._

val step1: Kleisli[List, Int, Int] =
  Kleisli(x => List(x + 1, x - 1))

val step2: Kleisli[List, Int, Int] =
  Kleisli(x => List(x, -x))

val step3: Kleisli[List, Int, Int] =
  Kleisli(x => List(x * 2, x / 2))

val pipeline1 = step1
val pipeline2 = step1 andThen step2
val pipeline3 = step1 andThen step2 andThen step3


pipeline1.run(20)
pipeline2.run(20)
pipeline3.run(20)
