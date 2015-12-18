import scalaz.Validation
import scalaz.syntax.validation._
import scalaz.syntax.std.string._
import scalaz.std.list._
import scalaz.syntax.applicative._

/**
  * @author harrylaou
  * @version 0.1
  */
object ValidationExercise extends App {
  type Result[A] = Validation[List[String], A]

  val inputData = Map[String, String](
    "house" -> "29",
    "street" -> "Accacia Road",
    "name" -> "Bananaman"
  )


  def numeric(msg: String = "is not a number")(house: String): Result[Int] = {
    //    Try {house.toInt} match {
    //      case Success(i)=>i.success
    //      case Failure(e)=>List(msg).failure
    //    }
    house.parseInt.leftMap(_ => List(msg))
  }

 lazy  val houseNumberNumeric: (String) => Result[Int] = numeric(msg = "House is not a number")

  def positive(msg: String = "is not a number")(number: Int): Result[Int] =
    if (number > 0) {
      number.success
    } else {
      List(msg).failure
    }

 lazy val houseNumberPositive: (Int) => Result[Int] = positive(msg = "House is not a number")


  def houseNumberOk(value: String): Result[Int] =
    houseNumberNumeric(value).disjunction.flatMap(houseNumberPositive(_).disjunction).validation


  def nonEmpty(msg: String)(value: String): Result[String] =
    if (!value.trim.isEmpty) value.trim.success else List(msg).failure


 lazy val streetNonEmpty: (String) => Result[String] = nonEmpty("House is empty") _

  def adressOk(data: Map[String, String]): Result[Address] = {

    val house: String = data.getOrElse("house", "")
    val street: String = data.getOrElse("street", "")
    //    (houseNumberOk(house) |@| streetNonEmpty(street)) {
    //      (house,street) => Address(house,street)
    //    }
    //this is the same as the commented
    (houseNumberOk(house) |@| streetNonEmpty(street)) (Address.apply)
  }



    println(adressOk(inputData))


}


case class Address(house: Int, street: String)

case class Person(name: String, address: Address)
