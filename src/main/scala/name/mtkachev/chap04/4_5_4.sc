import cats.Eval

def factorial(n: BigInt): BigInt =
  if(n == 1) n
  else n * factorial(n - 1)

//factorial(10000)

def factorialE(n: BigInt): Eval[BigInt] =
  if(n == 1) Eval.now(n)
  else Eval.defer(factorialE(n - 1).map(_ * n))


val fac = factorialE(10000)
fac.value
