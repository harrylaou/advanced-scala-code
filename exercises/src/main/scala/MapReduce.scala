//import scala.concurrent.{Future, ExecutionContext}
//import scalaz.{Monad, Applicative, Monoid}
//import scalaz.syntax.monoid._
//import scalaz.syntax.monad._
//import scalaz.std.list._
//import scala.language.higherKinds
//import scalaz.syntax.functor._
////import scalaz.syntax.applicative._
//
//
//object MapReduce extends App {
//  def foldMap[A, B](in: List[A])(f: A => B)(implicit m: Monoid[B]): B =
//    in.foldLeft(mzero[B]) {
//      _ |+| f(_)
//    }
//
//  def foldMapP[A, B](in: List[A])(f: A => B)(implicit m: Monoid[B], ec: ExecutionContext): Future[B] = {
//    val cpus: Int = Runtime.getRuntime.availableProcessors
//    val chunkSize: Int = in.size / cpus
//    val groups: List[List[A]] = in.grouped(chunkSize).toList
//    val lsF: List[Future[B]] = groups.map(l => Future(foldMap(l)(f)))
//    val fl: Future[List[B]] = Future.sequence(lsF)
//    fl.map(l => foldMap(l)(identity))
//  }
//
//  def foldMapA[A, B, F[_]](in: List[A])(f: A => B)(implicit m: Monoid[B], app: Applicative[F], ec: ExecutionContext): F[B] =
//  {
//    val cpus: Int = Runtime.getRuntime.availableProcessors
//    val chunkSize: Int = in.size / cpus
//    val groups: List[List[A]] = in.grouped(chunkSize).toList
//
//    val lifted: List[F[List[A]]] = groups.map((l:List[A])=>app.pure(l))
//    val lsA = lifted.map(func => func.map(l=>foldMap(l)(f)))
////    val lsA:List[F[B]]  =groups.map(l=>foldMap(l)(x=>x.pure.map[B](f)))
//// this doesn't compile due to ambiguous implicit values
//    val al: F[List[B]] = app.sequence(lsA)
//    app.map(al)(l => foldMap(l)(identity))
//  }
//
//
//  def foldMapM[A, M[_]: Monad, B: Monoid](iter: Iterable[A])(f: A => M[B]): M[B] =
//    iter.foldLeft(mzero[B].point[M]) { (accum, elt) =>
//    for {
//      a <- accum
//      b <- f(elt)
//    } yield a |+| b }
//
//
//  def examples() = {
//    import scalaz.std.string._
//    val data = List(1, 2, 3)
//    foldMap(data)(_.toString)
//  }
//
//  override def main(args: Array[String]) = {
//    println(examples())
//  }
//}
