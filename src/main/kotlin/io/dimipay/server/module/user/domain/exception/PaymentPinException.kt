package io.dimipay.server.module.user.domain.exception

import io.dimipay.server.common.exception.BadRequestException

class PaymentPinFormatException : BadRequestException("결제 비밀번호는 4자리 숫자로 이루어져야 해요.") {}
class PaymentPinSameNumberException : BadRequestException("결제 비밀번호는 같은 숫자로만 이루어질 수 없어요.") {}
class PaymentPinConsecutiveNumberException : BadRequestException("결제 비밀번호는 연속된 숫자로 이루어질 수 없어요.") {}
class PaymentPinNotSetException : BadRequestException("결제 비밀번호가 설정되지 않았어요.") {}