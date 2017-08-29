import cats.Monad
import cats.instances.option._
import cats.instances.list._
import cats.instances.future._

import scala.concurrent.Future


val opt1 = Monad[Option].pure(3)
val opt2 = Monad[Option].flatMap(opt1)(a => Some(a + 2))
val opt3 = Monad[Option].map(opt2)(a => 100 * a)
val list1 = Monad[List].pure(3)
val list2 = Monad[List].
  flatMap(List(1, 2, 3))(x => List(x, x*10))
val list3 = Monad[List].map(list2)(_ + 123)


import scala.concurrent.ExecutionContext.Implicits.global
val fm = Monad[Future]
