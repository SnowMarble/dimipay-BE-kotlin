package io.dimipay.server.user.domain.model.user

import io.dimipay.server.auth.domain.vo.RefreshToken
import io.dimipay.server.common.domain.BaseEntity
import io.dimipay.server.user.exception.OrganizationDomainNotAllowedException
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User private constructor(
    @Embedded
    val name: UserName,

    @Column(nullable = false)
    val email: String,

    @Column(name = "profile_image", nullable = false)
    val profileImage: String,

    @Column(nullable = false)
    val disabled: Boolean = false,

    @Embedded
    val biokey: Biokey? = null,

    @Embedded
    val deviceId: DeviceId? = null,

    @Embedded
    var paymentPin: PaymentPin? = null,

    @Column(name = "pin_try_count")
    val pinTryCount: Int = 0,

    @Embedded
    val refreshToken: RefreshToken? = null,

    @Column(name = "google_id")
    val googleId: String,
) : BaseEntity() {

  companion object {

    private const val ORGANIZATION_DOMAIN = "dimigo.hs.kr"

    fun create(name: UserName, email: String, profileImage: String, googleId: String, organizationDomain: String?): User {
      require(organizationDomain == ORGANIZATION_DOMAIN) { throw OrganizationDomainNotAllowedException() }

      return User(name = name, email = email, profileImage = profileImage, googleId = googleId)
    }
  }
}