package name.mtkachev.chap11

final case class GCounter(counters: Map[String, Int]) {
  def increment(machine: String, amount: Int): GCounter = 
    GCounter(
      counters.updated(machine, counters.getOrElse(machine, 0) + amount)
    )
  
  def get: Int = counters.values.sum

  def merge(that: GCounter): GCounter = GCounter(that.counters.foldLeft(counters){case (acc, v) =>
    acc.updated(v._1, counters.getOrElse(v._1, 0).max(v._2))
  })
}
