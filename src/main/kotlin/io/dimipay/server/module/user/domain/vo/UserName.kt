package io.dimipay.server.module.user.domain.vo

import io.dimipay.server.common.util.Name
import io.dimipay.server.module.user.domain.exception.UserNameException
import jakarta.persistence.Column

data class UserName(
    @Column(name = "name", nullable = false)
    val name: String
) {
  init {
    if (!Name.regularName.matches(name)) {
      throw UserNameException()
    }
  }

  companion object {
    /**
     * 주어진 이름을 그대로 반영하기 위해 regular name에서 허용하지 않는 문자는 제거합니다.
     */
    fun createAsDefault(name: String): UserName {
      return UserName(Name.toRegularName(name) { generateDefaultName() })
    }

    private fun generateDefaultName(): String {
      return "사용자${(0..9999).random().toString().padStart(4, '0')}"
    }
  }
}