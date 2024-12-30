package io.dimipay.server.module.user.domain.exception

import io.dimipay.server.common.exception.BadRequestException

class UserNameException : BadRequestException("이름은 10글자 이하의 한글과 영숫자로 이루어져야 해요.")