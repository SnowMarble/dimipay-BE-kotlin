package io.dimipay.server.user.presentation.controller

import io.dimipay.server.auth.application.annotation.Public
import io.dimipay.server.user.application.security.annotation.AllowOnboarding
import io.dimipay.server.user.application.service.GoogleLoginService
import io.dimipay.server.user.presentation.dto.OAuth2LoginRequestDto
import io.dimipay.server.user.presentation.dto.OAuth2LoginResponseDto
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
    private val googleLoginService: GoogleLoginService
) {

  /**
   * To complete the login, the user must verify the payment pin. This step is called "onboard".
   * This is why we don't return a refresh token. User can get it after the onboarding.
   */
  @Public
  @PostMapping(UserEndpoints.OAUTH2_LOGIN)
  fun oauth2Login(@Valid @RequestBody body: OAuth2LoginRequestDto): ResponseEntity<OAuth2LoginResponseDto> {
    val loginResult = googleLoginService.login(body.idToken)
    val statusCode = if (loginResult.isNewUser) HttpStatus.CREATED else HttpStatus.OK

    return ResponseEntity(OAuth2LoginResponseDto(loginResult.accessToken), statusCode)
  }

  @PostMapping
  fun refreshToken() {
  }

  @AllowOnboarding
  @PostMapping(UserEndpoints.ONBOARDING)
  fun onboarding() {
  }
}