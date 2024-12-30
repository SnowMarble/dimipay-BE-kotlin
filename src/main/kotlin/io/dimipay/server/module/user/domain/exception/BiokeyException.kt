package io.dimipay.server.module.user.domain.exception

import io.dimipay.server.common.exception.BadRequestException

class BiokeyException : BadRequestException("Biokey not set")