package io.dimipay.server.common.validation.annotation

import io.dimipay.server.common.validation.validator.EnumValidatorConstraint
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EnumValidatorConstraint::class])
annotation class ValidEnum(
    val enumClass: KClass<out Enum<*>>,
    val message: String = "must be one of {enumClass}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)