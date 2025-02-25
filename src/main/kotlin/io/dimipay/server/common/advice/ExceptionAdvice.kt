package io.dimipay.server.common.advice

import io.dimipay.server.common.dto.ResponseDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class ExceptionAdvice {

  private val logger = LoggerFactory.getLogger(ExceptionAdvice::class.java)

  private val defaultErrorCode = "INTERNAL_SERVER_ERROR"

  @ExceptionHandler(AuthenticationException::class)
  fun handleAuthenticationException(ex: AuthenticationException): ResponseEntity<ResponseDto<*>> {
    val errorCode = classNameToErrorCode(ex::class.simpleName as String)
    val responseDto = ResponseDto(
        HttpStatus.FORBIDDEN.value(),
        errorCode,
        ex.message,
        null
    )
    return ResponseEntity(responseDto, HttpStatus.UNAUTHORIZED)
  }

  @ExceptionHandler(AccessDeniedException::class)
  fun handleAuthorizationException(ex: AccessDeniedException): ResponseEntity<ResponseDto<*>> {
    val errorCode = classNameToErrorCode(ex::class.simpleName as String)
    val responseDto = ResponseDto(
        HttpStatus.FORBIDDEN.value(),
        errorCode,
        ex.message,
        null
    )
    return ResponseEntity(responseDto, HttpStatus.FORBIDDEN)
  }

  @ExceptionHandler(ResponseStatusException::class)
  fun handleApiException(ex: ResponseStatusException): ResponseEntity<ResponseDto<*>> {
    val errorCode = classNameToErrorCode(ex::class.simpleName as String)
    val responseDto = ResponseDto(
        ex.statusCode.value(),
        errorCode,
        ex.message,
        null
    )
    return ResponseEntity(responseDto, ex.statusCode)
  }

  @ExceptionHandler(Exception::class)
  fun handleException(ex: Exception): ResponseEntity<ResponseDto<*>> {
    if (ex is ErrorResponse) {
      val errorCode = classNameToErrorCode(ex::class.simpleName as String)
      val responseDto = ResponseDto(
          ex.statusCode.value(),
          errorCode,
          ex.message,
          null
      )
      return ResponseEntity(responseDto, ex.statusCode)
    }

    println(ex.message)

    val responseDto = ResponseDto(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        defaultErrorCode,
        null,
        null
    )

    logger.error("Internal server error", ex)

    return ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR)
  }

  private fun classNameToErrorCode(className: String): String {
    return camelCaseToSnakeCase(className).uppercase()
  }

  private fun camelCaseToSnakeCase(text: String): String {
    val pattern = Regex("(?<=.)[A-Z]")
    return text.replace(pattern, "_$0")
  }
}