package io.dimipay.server.module.user.presentation.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users/auth")
class UserAuthController {

  @GetMapping("/login")
  fun login(): String {
    return "Login"
  }
}