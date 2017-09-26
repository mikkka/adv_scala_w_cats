import name.mtkachev.chap09.FoldMap._

import cats.instances.string._

foldMap(Vector(1, 2, 3))(_.toString + "! ")
foldMap("Hello world!".toVector)(_.toString.toUpperCase)

