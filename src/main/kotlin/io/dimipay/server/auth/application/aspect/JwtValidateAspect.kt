package io.dimipay.server.auth.application.aspect

import com.fasterxml.jackson.databind.ObjectMapper
import io.dimipay.server.auth.application.annotation.ValidateJwt
import io.dimipay.server.auth.application.security.JwtService
import io.dimipay.server.common.exception.UnauthorizedException
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Aspect
@Component
class JwtValidateAspect(
    private val jwtService: JwtService,
    private val objectMapper: ObjectMapper
) {

  @Before("@annotation(io.dimipay.server.auth.application.annotation.ValidateJwt)")
  fun doValidateJwt(joinPoint: JoinPoint) {

    val signature = joinPoint.signature as MethodSignature
    val validateJwt = signature.method.getAnnotation(ValidateJwt::class.java)

    val request = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
    val token = request.request.getHeader(validateJwt.headerName)

    if (token.isNullOrEmpty()) {
      throw TokenNotFoundException(validateJwt.headerName)
    }

    jwtService.verify(token).let { objectMapper.convertValue(it, validateJwt.claims.java) }
  }

  class TokenNotFoundException(header: String) : UnauthorizedException("$header header not found")
}