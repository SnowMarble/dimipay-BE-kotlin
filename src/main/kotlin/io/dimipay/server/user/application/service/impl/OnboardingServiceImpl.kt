package io.dimipay.server.user.application.service.impl

import io.dimipay.server.auth.application.security.JwtService
import io.dimipay.server.auth.domain.vo.RefreshToken
import io.dimipay.server.common.dto.Tokens
import io.dimipay.server.user.application.security.UserAccessTokenClaims
import io.dimipay.server.user.application.security.UserRefreshTokenClaims
import io.dimipay.server.user.application.service.OnboardingService
import io.dimipay.server.user.domain.model.user.Biokey
import io.dimipay.server.user.domain.model.user.DeviceId
import io.dimipay.server.user.domain.model.user.User
import io.dimipay.server.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class OnboardingServiceImpl(
    private val jwtService: JwtService,
    private val userRepository: UserRepository
) : OnboardingService {

  override fun onboarding(user: User, deviceId: String?, biokey: String?): Tokens {
    if (deviceId != null) {
      user.deviceId = DeviceId.create(deviceId)
    }

    if (biokey != null) {
      user.biokey = Biokey.create(biokey)
    }

    val accessToken = jwtService.generateAccessToken(UserAccessTokenClaims(user.id.toString(), false))
    val refreshToken = jwtService.generateRefreshToken(UserRefreshTokenClaims(user.id.toString()))

    user.refreshToken = RefreshToken.create(refreshToken)

    userRepository.save(user)

    return Tokens(accessToken, refreshToken)
  }
}