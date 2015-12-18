

import scalaz.Monoid
import scalaz.syntax.monoid._
/**
  * @author harrylaou
  * @version 0.1
  */
object SuperAdderMy {
  def add[A: Monoid](items: List[A]): A =
    items.foldLeft(mzero[A]){ _ |+| _ }

  def example() = {
    import scalaz.std.anyVal._
    import scalaz.std.option._
    import scalaz.std.list._

    val six =add(List(1,2,3))
    val optSix = add(List(Option(1),Option(2),Option(3)))

    val someSix = add(List(some(1),some(2),some(3),none))

    val listSix = add(List(List(1),List(2),List(3)))

  }
}
