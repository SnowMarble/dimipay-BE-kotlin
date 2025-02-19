package io.dimipay.server.user.presentation.dto

import io.dimipay.server.common.validation.annotation.ValidEnum
import jakarta.validation.constraints.NotEmpty

/**
 * We technically identify oauth2 provider by iss claim. To do this, we need to parse the id token first.
 * But, usually, provider's SDK parses/verifies the id token in string format or its own parsed type,
 * so it could be tricky to convert already parsed id token to their type.
 * For example, Google SDK accepts raw string data or GoogleIdToken object when verifying id token.
 * So, I thought it's better to obtain provider from request body.
 *
 * The reason why I consider provider is to prepare for future requirements of multi-provider login.
 */
data class OAuth2LoginRequestDto(
    @field:NotEmpty
    val idToken: String,

    @field:ValidEnum(OAuth2Provider::class)
    val provider: OAuth2Provider
)

enum class OAuth2Provider {
  GOOGLE
}