package io.dimipay.server.user.exception

import io.dimipay.server.common.exception.BadRequestException

class OrganizationDomainNotAllowedException : BadRequestException("This organization domain is not allowed")