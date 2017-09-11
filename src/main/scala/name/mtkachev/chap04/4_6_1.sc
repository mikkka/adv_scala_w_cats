import cats.data.Writer
import cats.instances.vector._

Writer(Vector(
  "It was the best of times",
  "It was the worst of times"
), 123)

import cats.syntax.applicative._

type Logged[A] = Writer[Vector[String], A]
123.pure[Logged]
"ohlol".pure[Logged]


import cats.syntax.writer._

Vector("msg1", "msg2", "msg3").tell

val a = Writer(Vector("msg1", "msg2", "msg3"), 123)
val b = 123.writer(Vector("msg1", "msg2", "msg3"))

a.value
a.written

val (log, result) = b.run

val writer1 = for {
  a <- 10.pure[Logged]
  _ <- Vector("a", "b", "c").tell
  b <- 32.writer(Vector("x", "y", "z"))
} yield a + b

writer1.run


10.pure[Logged].flatMap { a =>
  Vector("a", "b", "c").tell.flatMap {_ =>
    32.writer(Vector("x", "y", "z")).map {b =>
      a + b
    }
  }.mapWritten(_.map(_.toUpperCase()))
}

val writer2 = writer1.mapWritten(_.map(_.toUpperCase))

val writer3 = writer1.bimap(
  log    => log.map{x => x + x},
  result => result * 100
)

val writer4 = writer1.mapBoth { (log, result) =>
  val log2    = log.map(_ + "!")
  val result2 = result * 1000
  (log2, result2)
}

val writer5 = writer1.reset

val writer6 = writer1.swap


10.pure[Logged].flatMap(_ => Vector("a", "b", "c").tell)
123.writer(Vector("msg1", "msg2", "msg3")).map (x => x + 321)

