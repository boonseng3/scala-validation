package scalazdemo

import java.util.Date

case class Pool(id: Long, description: Option[String], name: Option[String], organizationId: Long, projectId: Long,
  val deleted: Boolean, val createdTime: java.util.Date, val createdBy: Long,
  val modifiedTime: java.util.Date, val modifiedBy: Long) {

}

object PoolValidator {
  import scalaz.Scalaz._
  import scalaz._

  def main(args: Array[String]) {

    println("Validating...")
    // Failed, name and desc empty
    val result1 = validate(new Pool(1, None, None, 1, 1, false, new Date, 1, new Date, 1))
    result1 match {
      case Success(p) => println(s"validate successful: ${p}")
      case Failure(p) => p.foreach { x => println(s"error encountered: ${x}") }
    }
    println("Validating...")
    // Failed, name empty
    val result2 = validate(new Pool(1, Option("desc"), None, 1, 1, false, new Date, 1, new Date, 1))
    result2 match {
      case Success(p) => println(s"validate successful: ${p}")
      case Failure(p) => p.foreach { x => println(s"error encountered: ${x}") }
    }
    println("Validating...")
    // Passed
    val result3 = validate(new Pool(1, Option("desc"), Option("name"), 1, 1, false, new Date, 1, new Date, 1))
    result3 match {
      case Success(p) => println(s"validate successful: ${p}")
      case Failure(p) => p.foreach { x => println(s"error encountered: ${x}") }
    }
  }

  def validate(pool: Pool) = {
    val result = (NotEmpty(pool.id, "id") |@|
      NotEmpty(pool.name, "name") |@|
      NotEmpty(pool.description, "description")) {
        // Return if all successful
        Seq(_, _, _)
      }
    result
  }
  
  /**
   * Constraint that check for non empty value
   */
  def NotEmpty(value: Any, field: String) = {
    Option(value) match {
      case Some(x) => x.successNel
      case None => s"${field}.constraints.NotEmpty".failureNel[Any]
    }
  }
  /**
   * Constraint that check for non empty Option value
   */
  def NotEmpty(value: Option[_], field: String) = {
    value match {
      case Some(x) => x.successNel
      case None => s"${field}.constraints.NotEmpty".failureNel[Any]
    }
  }
}