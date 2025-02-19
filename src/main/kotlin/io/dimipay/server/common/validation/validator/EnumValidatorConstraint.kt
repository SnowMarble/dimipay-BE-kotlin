package io.dimipay.server.common.validation.validator

import io.dimipay.server.common.validation.annotation.ValidEnum
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class EnumValidatorConstraint : ConstraintValidator<ValidEnum, Any> {

  private lateinit var enumClass: Array<out Enum<*>>

  override fun initialize(constraintAnnotation: ValidEnum) {
    enumClass = constraintAnnotation.enumClass.java.enumConstants
  }

  override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
    if (value == null) {
      return true
    }

    return enumClass.any { it.name == value.toString() }
  }
}