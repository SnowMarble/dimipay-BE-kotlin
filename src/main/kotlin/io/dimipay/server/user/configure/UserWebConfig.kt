package io.dimipay.server.user.configure

import io.dimipay.server.user.application.security.UserArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class UserWebConfig(
    private val userArgumentResolver: UserArgumentResolver
) : WebMvcConfigurer {

  override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
    resolvers.add(userArgumentResolver)
  }
}