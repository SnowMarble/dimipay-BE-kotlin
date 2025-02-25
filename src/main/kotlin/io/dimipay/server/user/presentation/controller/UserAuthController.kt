package io.dimipay.server.user.presentation.controller

import io.dimipay.server.auth.application.annotation.ValidateJwt
import io.dimipay.server.user.application.security.annotation.CurrentUser
import io.dimipay.server.user.application.security.annotation.UserAuth
import io.dimipay.server.user.application.service.GoogleLoginService
import io.dimipay.server.user.application.service.OnboardingService
import io.dimipay.server.user.application.service.RefreshTokenService
import io.dimipay.server.user.domain.model.user.User
import io.dimipay.server.user.presentation.constant.JwtHeaders
import io.dimipay.server.user.presentation.dto.request.OAuth2LoginRequestDto
import io.dimipay.server.user.presentation.dto.request.OnboardingRequestDto
import io.dimipay.server.user.presentation.dto.request.RefreshTokenRequestDto
import io.dimipay.server.user.presentation.dto.response.OAuth2LoginResponseDto
import io.dimipay.server.user.presentation.dto.response.OnboardingResponseDto
import io.dimipay.server.user.presentation.dto.response.RefreshTokenResponseDto
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class UserAuthController(
    private val googleLoginService: GoogleLoginService,
    private val onboardingService: OnboardingService,
    private val refreshTokenService: RefreshTokenService
) {

  /**
   * To complete the login, the user must verify the payment pin. This step is called "onboard".
   * This is why we don't return a refresh token. User can get it after the onboarding.
   */
  @PostMapping(UserEndpoints.OAUTH2_LOGIN)
  fun oauth2Login(@Valid @RequestBody body: OAuth2LoginRequestDto): ResponseEntity<OAuth2LoginResponseDto> {
    val loginResult = googleLoginService.login(body.idToken)
    val statusCode = if (loginResult.isNewUser) HttpStatus.CREATED else HttpStatus.OK

    return ResponseEntity(OAuth2LoginResponseDto(loginResult.accessToken), statusCode)
  }

  @PostMapping(UserEndpoints.REFRESH_TOKEN)
  fun refreshToken(@Valid @RequestBody body: RefreshTokenRequestDto): RefreshTokenResponseDto {
    val result = refreshTokenService.refresh(body.refreshToken)

    return RefreshTokenResponseDto(result.accessToken, result.refreshToken)
  }

  @UserAuth(allowOnboarding = true)
  @ValidateJwt(JwtHeaders.PAYMENT_PIN_AUTH)
  @PostMapping(UserEndpoints.ONBOARDING)
  fun onboarding(
      @CurrentUser user: User,
      @Valid @RequestBody body: OnboardingRequestDto
  ): OnboardingResponseDto {
    val result = onboardingService.onboarding(user, body.deviceId, body.biokey)

    return OnboardingResponseDto(result.accessToken, result.refreshToken)
  }
}