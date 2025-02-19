package io.dimipay.server.common.advice

import com.fasterxml.jackson.databind.ObjectMapper
import io.dimipay.server.common.dto.ResponseDto
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@ControllerAdvice
class ResponseBodyAdvice(
    private val objectMapper: ObjectMapper
) : ResponseBodyAdvice<Any> {

  private val successCode = "OK"

  override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
    return true
  }

  override fun beforeBodyWrite(
      body: Any?,
      returnType: MethodParameter,
      selectedContentType: MediaType,
      selectedConverterType: Class<out HttpMessageConverter<*>>,
      request: ServerHttpRequest,
      response: ServerHttpResponse
  ): Any? {

    if (body is ResponseDto<*>) {
      return body
    }

    val statusCode: Int = if (body is ResponseEntity<*>) body.statusCode.value() else 200
    val responseDto = ResponseDto(
        status = statusCode,
        code = successCode,
        data = body
    )

    if (body is String) {
      response.headers.contentType = MediaType.APPLICATION_JSON
      return objectMapper.writeValueAsString(responseDto)
    }

    return responseDto
  }
}