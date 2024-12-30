package io.dimipay.server.module.user.domain.exception

import io.dimipay.server.common.exception.BadRequestException

class DeviceIdException : BadRequestException("DeviceId not set")