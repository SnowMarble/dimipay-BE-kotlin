package io.dimipay.server.common.exception

import org.springframework.http.HttpStatus

open class ApiException(
    val statusCode: HttpStatus,
    message: String,
    val error: Any? = null,
) : RuntimeException(message)

open class BadRequestException(message: String, error: Any? = null) : ApiException(HttpStatus.BAD_REQUEST, message, error)
open class UnauthorizedException(message: String, error: Any? = null) : ApiException(HttpStatus.UNAUTHORIZED, message, error)
open class NotFoundException(message: String, error: Any? = null) : ApiException(HttpStatus.NOT_FOUND, message, error)