package name.mtkachev.chap03.codec

import Codec._
import CodecInstances._

object CodecApp extends App {
  final case class Box[A](value: A)

  implicit def boxCodec[A](implicit p: Codec[A]) =
    p.imap[Box[A]](
      {Box(_)}, {_.value}
    )


  println(decode[Box[String]]("in da box"))
  println(encode(Box("das box")))
}
