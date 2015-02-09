package scalazdemo

import scala.beans.BeanProperty
import scala.annotation.meta.field
import org.hibernate.validator.constraints.NotBlank
import java.util.Date
import javax.validation.constraints._
import org.hibernate.validator.constraints.NotEmpty
import javax.validation.groups.Default
import javax.validation.ConstraintValidator
import javax.validation.Payload
import javax.validation.Constraint
import java.lang.annotation.Documented
import java.lang.annotation.Target
import java.lang.annotation.Retention
import java.lang.annotation.ElementType
import java.lang.annotation._
import scala.annotation.StaticAnnotation
import javax.validation.ConstraintValidatorContext
import org.springframework.validation.Validator
import org.springframework.validation.Errors
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.ValidationUtils
import org.springframework.validation.DataBinder


trait NameRequired {

}

case class Person(
  // NotBlank is only applicable to NameRequired, if groups is not specified, default is Default groups
  @BeanProperty 
  @(NotBlank @field)(groups = Array(classOf[NameRequired]))
  @(Size @field)(min = 0)
  name: String,
  
  @BeanProperty 
  @NotNull birthDate: Date,
  
  address: List[String],
  
  @BeanProperty @(NotNull @field) 
  desc: Option[String]) {
}


object SpringValidationWithAnnotation{

  def main(args: Array[String]) {
    val validatorFactory = javax.validation.Validation.buildDefaultValidatorFactory();
        val v1 = validatorFactory.getValidator
        // Passed because name is applicable to Default group only
        println(v1.validate(new Person("", null, List(), None)))
        // Failed, name is blank
        println(v1.validate(new Person("", null, List(), None), classOf[NameRequired], classOf[Default]))
        // Failed, name is blank and des is null
        println(v1.validate(new Person("", null, List(), null), classOf[NameRequired], classOf[Default]))
        // Failed, name is blank but not desc because None not recognized
        println(v1.validate(new Person("", null, List(), None), classOf[NameRequired], classOf[Default]))
  
}
  }
