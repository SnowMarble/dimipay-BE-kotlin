package io.dimipay.server.module.user.domain.entity

import io.dimipay.server.common.ddd.BaseEntity
import io.dimipay.server.module.auth.domain.vo.RefreshToken
import io.dimipay.server.module.user.domain.vo.Biokey
import io.dimipay.server.module.user.domain.vo.DeviceId
import io.dimipay.server.module.user.domain.vo.PaymentPin
import io.dimipay.server.module.user.domain.vo.UserName
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Embedded
    val name: UserName,

    @Column(nullable = false)
    val email: String,

    @Column(name = "profile_image", nullable = false)
    val profileImage: String,

    @Column(nullable = false)
    val isDisabled: Boolean = false,

    @Embedded
    val biokey: Biokey,

    @Embedded
    val deviceId: DeviceId,

    @Embedded
    val paymentPin: PaymentPin,

    @Column(name = "pin_try_count")
    val pinTryCount: Int = 0,

    @Embedded
    val refreshToken: RefreshToken,

    id: Long
) : BaseEntity(id) {

}