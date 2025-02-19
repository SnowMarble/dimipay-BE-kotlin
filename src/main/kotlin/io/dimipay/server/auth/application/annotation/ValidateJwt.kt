package io.dimipay.server.auth.application.annotation

import io.dimipay.server.auth.application.security.JwtClaims
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidateJwt(
    val headerName: String,
    val claims: KClass<out JwtClaims>
)
