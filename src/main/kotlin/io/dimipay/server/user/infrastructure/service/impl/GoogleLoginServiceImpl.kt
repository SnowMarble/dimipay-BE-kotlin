package io.dimipay.server.user.infrastructure.service.impl

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import io.dimipay.server.auth.application.security.JwtService
import io.dimipay.server.common.exception.BadRequestException
import io.dimipay.server.user.application.security.UserAccessTokenClaims
import io.dimipay.server.user.application.service.GoogleLoginService
import io.dimipay.server.user.domain.model.user.User
import io.dimipay.server.user.domain.model.user.UserName
import io.dimipay.server.user.domain.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(GoogleProperties::class)
class GoogleLoginServiceImpl(
    googleProperties: GoogleProperties,
    private val jwtService: JwtService,
    private val userRepository: UserRepository
) : GoogleLoginService {

  private val verifier: GoogleIdTokenVerifier = GoogleIdTokenVerifier.Builder(googleProperties.transport, googleProperties.gsonFactory)
      .setAudience(googleProperties.audiences)
      .build()

  private class InvalidIdToken : BadRequestException("Invalid id token")

  @Transactional
  override fun login(idTokenString: String): GoogleLoginService.GoogleLoginResult {
    val idToken = try {
      verifier.verify(idTokenString)
    } catch (ex: Exception) {
      throw InvalidIdToken()
    }
    val payload = idToken.payload

    var isNewUser = false

    val user: User = userRepository.findByGoogleId(payload.subject) ?: run {
      isNewUser = true
      registerUser(payload)
    }

    val accessToken = jwtService.generateAccessToken(UserAccessTokenClaims(user.id.toString(), true))

    return GoogleLoginService.GoogleLoginResult(accessToken, isNewUser)
  }

  private fun registerUser(payload: GoogleIdToken.Payload): User {
    val user = User.create(
        UserName(payload["name"] as String),
        payload.email,
        payload["picture"] as String,
        payload.subject,
        payload["hd"]?.toString()
    )
    return userRepository.save(user)
  }
}