package io.dimipay.server.user.domain.repository

import io.dimipay.server.user.domain.model.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
  
  fun findByGoogleId(googleId: String): User?
}