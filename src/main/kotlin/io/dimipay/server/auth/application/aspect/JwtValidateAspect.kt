package io.dimipay.server.auth.application.aspect

import io.dimipay.server.auth.application.annotation.ValidateJwt
import io.dimipay.server.auth.application.exception.JwtValidaeAspectException
import io.dimipay.server.auth.application.security.JwtService
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
) {

  @Before("@annotation(io.dimipay.server.auth.application.annotation.ValidateJwt)")
  fun validateJwt(joinPoint: JoinPoint) {

    val signature = joinPoint.signature as MethodSignature
    val validateJwt = signature.method.getAnnotation(ValidateJwt::class.java)

    val request = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
    val token = request.request.getHeader(validateJwt.headerName)

    if (token.isNullOrEmpty()) {
      throw JwtValidaeAspectException.TokenNotFoundException(validateJwt.headerName)
    }

    try {
      jwtService.verify(token)
    } catch (e: Exception) {
      throw JwtValidaeAspectException.InvalidTokenException()
    }
  }
}