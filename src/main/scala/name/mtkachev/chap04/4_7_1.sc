import cats.data.Reader

case class Cat(name: String, favoriteFood: String)

val catName: Reader[Cat, String] =
  Reader(cat => cat.name)

val greetKitty: Reader[Cat, String] =
  catName.map(name => s"Hello ${name}")

val feedKitty: Reader[Cat, String] =
  Reader(cat => s"Have a nice bowl of ${cat.favoriteFood}")

val greetAndFeed: Reader[Cat, String] =
  for {
    msg1 <- greetKitty
    msg2 <- feedKitty
  } yield s"${msg1}! ${msg2}"


greetAndFeed(Cat("Sotona", "human flesh"))
