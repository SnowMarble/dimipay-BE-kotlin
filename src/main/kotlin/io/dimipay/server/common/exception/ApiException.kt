package io.dimipay.server.common.exception

import org.springframework.http.HttpStatus

open class ApiException(
    val statusCode: HttpStatus,
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)

open class BadRequestException(message: String, cause: Throwable? = null) : ApiException(HttpStatus.BAD_REQUEST, message, cause)