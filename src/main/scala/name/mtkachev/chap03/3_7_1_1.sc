import cats.Semigroup
import cats.instances.string._
import cats.syntax.invariant._

implicit val symbolSemigroup: Semigroup[Symbol] =
  Semigroup[String].imap { str: String =>
    Symbol.apply(str)
  } { sym: Symbol =>
    sym.name
  }

import cats.syntax.semigroup._

'a |+| 'few |+| 'words
