import cats.Eval

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

val ans = for {
  a <- Eval.now {println("Calculating A"); 40}
  b <- Eval.always {println("Calculating B"); 2}
} yield {
  println("Adding A and B")
  a + b
}

ans.value
ans.value
