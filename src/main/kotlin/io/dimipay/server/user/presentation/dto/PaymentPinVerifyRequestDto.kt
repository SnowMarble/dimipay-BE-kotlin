package io.dimipay.server.user.presentation.dto

import jakarta.validation.constraints.NotEmpty

data class PaymentPinVerifyRequestDto(
    @field:NotEmpty
    val pin: String
)
