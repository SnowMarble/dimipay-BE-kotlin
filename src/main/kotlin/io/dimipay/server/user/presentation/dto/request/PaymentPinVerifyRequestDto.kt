package io.dimipay.server.user.presentation.dto.request

import jakarta.validation.constraints.NotEmpty

data class PaymentPinVerifyRequestDto(
    
    @field:NotEmpty
    val pin: String
)
