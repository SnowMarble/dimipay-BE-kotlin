package io.dimipay.server.user.application.security.annotation

import org.springframework.security.access.prepost.PreAuthorize

@Suppress("unused", "SpringElInspection")
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("@userAuthorization.authorize(#root, {allowOnboarding})")
annotation class UserAuth(val allowOnboarding: Boolean = false)