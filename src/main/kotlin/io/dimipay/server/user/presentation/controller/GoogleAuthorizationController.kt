package io.dimipay.server.user.presentation.controller

import io.dimipay.server.user.application.service.GoogleAuthorizeService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

/**
 * Google OAuth2 Authorization Controllers. This for development only.
 */
@Profile("dev")
@RestController
@RequestMapping
class GoogleAuthorizationController(
    private val googleAuthorizeService: GoogleAuthorizeService,
) {

  @GetMapping(UserEndpoints.OAUTH2_AUTHORIZE_GOOGLE)
  fun getAuthorizationUrl(): RedirectView {
    return RedirectView(googleAuthorizeService.getAuthorizationUrl())
  }

  @GetMapping(UserEndpoints.OAUTH2_CODE_GOOGLE)
  fun getIdToken(request: HttpServletRequest): String {
    return googleAuthorizeService.getIdToken(request)
  }
}