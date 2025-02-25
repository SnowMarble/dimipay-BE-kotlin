package io.dimipay.server.user.application.service.impl

import io.dimipay.server.auth.application.security.JwtService
import io.dimipay.server.auth.domain.vo.RefreshToken
import io.dimipay.server.common.dto.Tokens
import io.dimipay.server.common.exception.ResourceNotFoundException
import io.dimipay.server.user.application.security.UserAccessTokenClaims
import io.dimipay.server.user.application.security.UserRefreshTokenClaims
import io.dimipay.server.user.application.service.RefreshTokenService
import io.dimipay.server.user.application.service.exception.RefreshTokenException
import io.dimipay.server.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class RefreshTokenServiceImpl(
    private val jwtService: JwtService,
    private val userRepository: UserRepository
) : RefreshTokenService {

  override fun refresh(token: String): Tokens {

    val claims = jwtService.verify(token)

    val user = userRepository.findById((claims["sub"] as String).toLong()).orElseThrow { ResourceNotFoundException() }

    if (user.refreshToken == null) {
      throw RefreshTokenException.RefreshTokenNotFound()
    }

    if (!user.refreshToken!!.matches(token)) {
      throw RefreshTokenException.RefreshTokenExpired()
    }

    val accessToken = jwtService.generateAccessToken(UserAccessTokenClaims(user.id.toString(), false))
    val refreshToken = jwtService.generateRefreshToken(UserRefreshTokenClaims(user.id.toString()))

    user.refreshToken = RefreshToken.create(refreshToken)

    userRepository.save(user)

    return Tokens(accessToken, refreshToken)
  }
}