import scala.concurrent.{Future, ExecutionContext}
import scalaz.{Monoid, Monad, Applicative}
import scalaz.syntax.monoid._
import scalaz.syntax.monad._
import scalaz.std.list._
import scala.language.higherKinds
//import scalaz.syntax.functor._
//import scalaz.syntax.applicative._


object MapReduceA extends App {
  def foldMap[A, B](in: List[A])(f: A => B)(implicit m: Monoid[B]): B =
    in.foldLeft(mzero[B]) {
      _ |+| f(_)
    }



  def foldMapP[A, B](in: List[A])(f: A => B)(implicit m: Monoid[B], ec: ExecutionContext): Future[B] = {
    val cpus: Int = Runtime.getRuntime.availableProcessors
    val chunkSize: Int = in.size / cpus
    val groups: List[List[A]] = in.grouped(chunkSize).toList
    val lfb: List[Future[B]] = groups.map(lista=>Future(foldMap(lista)(f)))
    val flb: Future[List[B]] = Future.sequence(lfb)
    val result: Future[B] = flb.map(listb=>foldMap(listb)(identity))
    result


  }

    def foldMapM[A, M[_]: Monad, B: Monoid](iter: Iterable[A])(f: A => M[B]): M[B] =
      iter.foldLeft(mzero[B].point[M]) { (accum, elt) =>
      for {
        a <- accum
        b <- f(elt)
      } yield a |+| b
      }




  def examples() = {
    import scalaz.std.string._
    val data = List(1, 2, 3)
    foldMap(data)(_.toString)
  }

  override def main(args: Array[String]) = {
    println(examples())
  }
}
