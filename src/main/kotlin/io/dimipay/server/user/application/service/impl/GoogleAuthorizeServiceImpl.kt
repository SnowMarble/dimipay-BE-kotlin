package io.dimipay.server.user.application.service.impl

import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
import io.dimipay.server.user.application.service.GoogleAuthorizeService
import io.dimipay.server.user.infrastructure.service.impl.GoogleProperties
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(GoogleProperties::class)
class GoogleAuthorizeServiceImpl(
    private val googleProperties: GoogleProperties
) : GoogleAuthorizeService {

  /**
   * https://developers.google.com/identity/protocols/oauth2/scopes
   */
  private val scopes: Collection<String> = hashSetOf(
      "https://www.googleapis.com/auth/userinfo.email",
      "https://www.googleapis.com/auth/userinfo.profile",
      "openid"
  )

  override fun getAuthorizationUrl(): String {
    return GoogleAuthorizationCodeRequestUrl(
        googleProperties.clientId,
        googleProperties.redirectUri,
        scopes
    ).build()
  }

  override fun getIdToken(request: HttpServletRequest): String {
    val url = getFullURL(request)
    val authResponse = AuthorizationCodeResponseUrl(url)
    if (authResponse.error != null) {
      throw IllegalArgumentException("Error: ${authResponse.error}")
    }

    val response = GoogleAuthorizationCodeTokenRequest(
        googleProperties.transport,
        googleProperties.gsonFactory,
        googleProperties.clientId,
        googleProperties.clientSecret,
        authResponse.code,
        googleProperties.redirectUri)
        .execute()

    return response.idToken
  }

  /**
   * https://googleapis.dev/java/google-oauth-client/latest/com/google/api/client/auth/oauth2/AuthorizationCodeResponseUrl.html
   */
  private fun getFullURL(request: HttpServletRequest): String {
    val fullUrlBuf = request.requestURL
    if (request.queryString != null) {
      fullUrlBuf.append('?').append(request.queryString)
    }
    return fullUrlBuf.toString()
  }
}