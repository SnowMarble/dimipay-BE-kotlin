package io.dimipay.server.common.security

import org.springframework.security.access.AccessDeniedException

class RoleNotFoundException(expected: String) : AccessDeniedException("Expected role: $expected")