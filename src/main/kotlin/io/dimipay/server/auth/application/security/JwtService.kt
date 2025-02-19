package io.dimipay.server.auth.application.security

interface JwtService {

  fun generateAccessToken(claims: JwtClaims): String

  fun generateRefreshToken(claims: JwtClaims): String

  fun generateToken(claims: JwtClaims, expiration: Long): String

  fun verify(token: String): Map<String, Any>
}