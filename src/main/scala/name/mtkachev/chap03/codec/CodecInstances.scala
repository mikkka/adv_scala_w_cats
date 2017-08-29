package name.mtkachev.chap03.codec

import scala.util.Try

object CodecInstances {
  implicit val intCodec: Codec[Int] = new Codec[Int] {
    override def encode(value: Int): String =
      value.toString

    override def decode(value: String): Option[Int] = Try {
      value.toInt
    }.toOption
  }

  implicit val stringCodec: Codec[String] = new Codec[String] {
    override def encode(value: String): String = value

    override def decode(value: String): Option[String] = Option(value)
  }
}
