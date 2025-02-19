package io.dimipay.server.user.presentation.dto

import jakarta.validation.constraints.NotEmpty

data class PaymentPinRegisterRequestDto(
    @field:NotEmpty
    val pin: String
)