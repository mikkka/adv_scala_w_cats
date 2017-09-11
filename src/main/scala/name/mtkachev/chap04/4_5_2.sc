import cats.Eval

/*
val now    = Eval.now(1 + 2)
val later  = Eval.later(3 + 4)
val always = Eval.always(5 + 6)

now.value
later.value
always.value


val greeting = Eval.always {
  println("Step 1")
  "Hello"
}.map { str =>
  println("Step 2")
  str + " world"
}

greeting.value

*/
val ans = for {
  a <- Eval.now {println("Calculating A"); 40}
  c <- Eval.later {println("Calculating C"); 2}
  b <- Eval.always {println("Calculating B"); 2}
} yield {
  println("Adding A and B and C")
  a + b + c
}

ans

ans.value

ans.value

val later = Eval.later {
  println("eval daughter")
  123
}
later.value
later.value

val fookEval =
Eval.now{
  println("one")
  "fook"
}.flatMap{x =>
  println("flatMap two")
  Eval.always{
    println("two")
    x + " ya"
  }}.flatMap{x =>
  println("flatMap three")
    Eval.later{
      println("three")
      x + " self"
    }}

fookEval.value
fookEval.value
