package mayton.markov

import scala.collection.mutable
import scala.reflect.ClassTag

class WeightGraphInt[V: ClassTag](val emap: mutable.Map[(V, V), Int]) {

  def this() = {
    this(mutable.Map.empty[(V, V), Int])
  }

  def this(size : Int) = {
    this(new mutable.HashMap[(V, V), Int](size, 1.0))
  }

  def addEdge(c1:V, c2:V) : Boolean = {
    if (emap.contains((c1,c2))) {
      val cnt : Int = emap((c1,c2))
      emap += ((c1,c2) -> (cnt + 1))
      false
    } else {
      emap += ((c1,c2) -> 1)
      true
    }
  }

  def clean(): Unit = {
    emap.clear()
  }



}
