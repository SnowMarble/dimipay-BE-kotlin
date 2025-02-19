package io.dimipay.server.auth.application.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.dimipay.server.common.dto.ResponseDto
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class AuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {

  override fun commence(
      request: HttpServletRequest,
      response: HttpServletResponse,
      authException: AuthenticationException
  ) {

    response.contentType = MediaType.APPLICATION_JSON_VALUE
    response.status = HttpServletResponse.SC_UNAUTHORIZED
    
    val errorResponse = ResponseDto(
        401,
        "Unauthorized",
        authException.message,
        null
    )

    objectMapper.writeValue(response.outputStream, errorResponse)
  }
}