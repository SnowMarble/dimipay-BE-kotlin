package io.dimipay.server.user.application.security

import io.dimipay.server.common.exception.ForbiddenException

class OnboardingRequiredException : ForbiddenException("Onboarding required")