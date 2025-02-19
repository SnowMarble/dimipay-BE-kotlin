package io.dimipay.server.user.exception

import io.dimipay.server.common.exception.BadRequestException

class DeviceIdException : BadRequestException("DeviceId not set")