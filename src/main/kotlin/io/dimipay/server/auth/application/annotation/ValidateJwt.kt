package io.dimipay.server.auth.application.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidateJwt(
    val headerName: String,
)
