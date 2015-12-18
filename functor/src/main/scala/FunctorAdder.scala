import scalaz.Functor
import scalaz.syntax.functor._
import scala.language.higherKinds
import scalaz.std.option._
import scalaz.std.list._
import scalaz.std.map._
/**
  * @author harrylaou
  * @version 0.1
  */
object FunctorAdder {

  def addOne[F[_]](fa:F[Int])(implicit ffa:Functor[F]):F[Int] =
//    ffa.map[Int,Int](fa)(_+1) or

  fa.map(_+1)


  def examples( )= {
    val a = addOne(some(1))
    val b = addOne(List(1,2,3))

    type FMap[A] = Map[String,A]

    val c = addOne[FMap](Map("a"->1,"b"->2))

  }
}
