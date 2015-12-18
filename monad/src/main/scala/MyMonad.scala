import scalaz.std.option._


import scalaz.\/
import scalaz.syntax.either._


/**
  * @author harrylaou
  * @version 0.1
  */
object MyMonad {

//  def account(userName: String, password: String): Option[Int] =
//    (userName, password) match {
//      case ("user", "password") => some(1)
//      case ("anonymous", _) => some(2)
//      case _ => none
//    }

  def account(userName: String, password: String): String \/ Int =
    (userName, password) match {
      case ("user", "password") => 1.right
      case ("anonymous", _) => 2.right
      case _ => s"No account for $userName".left
    }

//  def authorized(userId: Int): Option[Boolean] =
//    userId match {
//      case 1 => some(true)
//      case _ => none
//    }
def authorized(userId: Int): String \/ Unit =
  userId match {
    case 1 =>().right
    case _ => "unauthorized".left
  }

//  def profile(userId: Int): Option[String] =
//    userId match {
//      case 1 => some("my profile")
//      case 2 => some("anonymous")
//      case _ => none
//    }

  def profile(userId: Int):  String \/ String =
    userId match {
      case 1 => "my profile".right
      case 2 => "anonymous".right
      case _ => "no profile".left
    }

  def displayProfile(userName: String, password: String): String \/ String=
    for {
      acct <- account(userName, password)
      _ <- authorized(acct)
      prof <- profile(acct)
    } yield prof

}
