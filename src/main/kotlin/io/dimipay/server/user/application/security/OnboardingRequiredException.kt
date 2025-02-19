package io.dimipay.server.user.application.security

import org.springframework.security.access.AccessDeniedException

class OnboardingRequiredException : AccessDeniedException("Onboarding is required")