package io.dimipay.server.user.exception

import io.dimipay.server.common.exception.BadRequestException

sealed class PaymentPinException(message: String) : BadRequestException(message) {

  class PaymentPinFormatException : BadRequestException("결제 비밀번호는 4자리 숫자로 이루어져야 해요.")

  class PaymentPinSameNumberException : BadRequestException("결제 비밀번호는 같은 숫자로만 이루어질 수 없어요.")

  class PaymentPinConsecutiveNumberException : BadRequestException("결제 비밀번호는 연속된 숫자로 이루어질 수 없어요.")

  class PaymentPinNotSetException : BadRequestException("결제 비밀번호가 설정되지 않았어요.")

  class PaymentPinAlreadySetException : BadRequestException("결제 비밀번호가 이미 설정되어 있어요.")

  class InvalidPaymentPinException : BadRequestException("결제 비밀번호가 올바르지 않았어요.")

  class PaymentPinTryCountLimitException : BadRequestException("결제 비밀번호 시도 횟수가 초과되었어요.")
}