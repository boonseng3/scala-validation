package scalazdemo

import org.springframework.validation.Errors
import org.springframework.validation.Validator
import org.springframework.validation.DataBinder

trait ValidationConstraints {
 /**
   * Constraint that check for non empty value
   */
  def fieldNotEmpty(e: Errors, name: String) {
    e.getFieldValue(name) match {
      case Some(_) =>
      case None => e.rejectValue(name, "errorCode")
      case x => Option(x) match {
        case Some(_) =>
        case None => e.rejectValue(name, "errorCode")
      }

    }
  }
}

/**
 * Validate that name must not be empty
 */
class PersonValidator1 extends Validator with ValidationConstraints {

  def supports(clazz: Class[_]): Boolean = {
    clazz.isAssignableFrom(classOf[Person])
  }

  def validate(obj: Object, e: Errors): Unit = {
    fieldNotEmpty(e, "name")
  }
}
/**
 * Validate that name and desc must not be empty
 */
class PersonValidator2 extends Validator with ValidationConstraints {

  def supports(clazz: Class[_]): Boolean = {
    clazz.isAssignableFrom(classOf[Person])
  }

  def validate(obj: Object, e: Errors): Unit = {
    fieldNotEmpty(e, "name")
    fieldNotEmpty(e, "desc")
  }
}

object SpringValidationWithValidator {

  def main(args: Array[String]) {

    {
      // Passed using validator 1
      val binder: DataBinder = new DataBinder(new Person("name", null, List(), None))
      binder.setValidator(new PersonValidator1)
      binder.validate()
      println(binder.getBindingResult.getAllErrors)
    }
    {
      // Passed using validator 2
      val binder: DataBinder = new DataBinder(new Person("name", null, List(), Option("desc")))
      binder.setValidator(new PersonValidator2)
      binder.validate()
      println(binder.getBindingResult.getAllErrors)
    }
    {
      // Failed using validator 2, desc cannot be empty
      val binder: DataBinder = new DataBinder(new Person("name", null, List(), None))
      binder.setValidator(new PersonValidator2)
      binder.validate()
      println(binder.getBindingResult.getAllErrors)
    }
    {
      // Failed using validator 2, name desc cannot be empty
      val binder: DataBinder = new DataBinder(new Person(null, null, List(), None))
      binder.setValidator(new PersonValidator2)
      binder.validate()
      println(binder.getBindingResult.getAllErrors)
    }

  }
}