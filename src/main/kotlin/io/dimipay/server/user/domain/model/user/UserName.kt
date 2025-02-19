package io.dimipay.server.user.domain.model.user

import io.dimipay.server.common.util.Name
import io.dimipay.server.user.exception.UserNameException
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class UserName(
    @Column(nullable = false)
    final val name: String
) {

  init {
    if (!Name.regularName.matches(name)) {
      throw UserNameException()
    }
  }

  companion object {

    fun createSanitized(name: String): UserName {
      return UserName(Name.toRegularName(name) { generateDefaultName() })
    }

    // TODO: l10n default name
    private fun generateDefaultName(): String {
      return "사용자${generateRandomId()}"
    }

    private fun generateRandomId(): String {
      return (0..9999).random().toString().padStart(4, '0')
    }
  }
}