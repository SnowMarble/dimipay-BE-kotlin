package io.dimipay.server.user.presentation.dto.request

import jakarta.validation.constraints.NotEmpty

class RefreshTokenRequestDto(

    @field:NotEmpty
    val refreshToken: String
)