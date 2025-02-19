package io.dimipay.server.auth.application.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.dimipay.server.common.dto.ResponseDto
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class AccessDeniedHandler(
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler {

  override fun handle(
      request: HttpServletRequest,
      response: HttpServletResponse,
      accessDeniedException: AccessDeniedException
  ) {
    response.contentType = MediaType.APPLICATION_JSON_VALUE
    response.status = HttpServletResponse.SC_FORBIDDEN
    
    val errorResponse = ResponseDto<Any>(403, "Forbidden", accessDeniedException.message)

    objectMapper.writeValue(response.outputStream, errorResponse)
  }
}