import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import cats.Traverse
import cats.instances.future._
import cats.instances.list._


Await.result(
  Traverse[List].traverse(List("foo", "bar", "baz")){x => Future(x.reverse + x)},
  1.second
)

Await.result(
  Traverse[List].sequence(List(Future(1), Future(2), Future(3))),
  1.second
)



import cats.syntax.traverse._

Await.result(
  List("foo", "bar", "baz").traverse({x => Future(x.reverse + x)}),
  1.second
)

Await.result(
  List(Future(1), Future(2), Future(3)).sequence,
  1.second
)
