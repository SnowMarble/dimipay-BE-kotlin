package io.dimipay.server.common.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

open class BadRequestException(message: String, error: Throwable? = null) : ResponseStatusException(HttpStatus.BAD_REQUEST, message, error)
open class UnauthorizedException(message: String, error: Throwable? = null) : ResponseStatusException(HttpStatus.UNAUTHORIZED, message, error)
open class ForbiddenException(message: String, error: Throwable? = null) : ResponseStatusException(HttpStatus.FORBIDDEN, message, error)
open class NotFoundException(message: String, error: Throwable? = null) : ResponseStatusException(HttpStatus.NOT_FOUND, message, error)