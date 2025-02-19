package io.dimipay.server.auth.infrastructure.service

import org.springframework.security.core.AuthenticationException

class InvalidJwtException(override val message: String?) : AuthenticationException("Invalid JWT ($message)")
