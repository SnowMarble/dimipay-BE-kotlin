package io.dimipay.server.user.application.service

import io.dimipay.server.common.dto.Tokens

interface RefreshTokenService {

  fun refresh(token: String): Tokens
}