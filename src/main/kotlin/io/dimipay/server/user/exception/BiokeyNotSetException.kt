package io.dimipay.server.user.exception

import io.dimipay.server.common.exception.BadRequestException

class BiokeyNotSetException : BadRequestException("Biokey not set")