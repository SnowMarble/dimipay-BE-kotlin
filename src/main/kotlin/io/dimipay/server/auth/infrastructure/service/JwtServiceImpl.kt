package io.dimipay.server.auth.infrastructure.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.dimipay.server.auth.application.security.JwtClaims
import io.dimipay.server.auth.application.security.JwtService
import io.dimipay.server.auth.configure.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

@Service
@EnableConfigurationProperties(JwtProperties::class)
class JwtServiceImpl(
    private val jwtProperties: JwtProperties,
    private val objectMapper: ObjectMapper
) : JwtService {

  private val key: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret))

  private val parser = Jwts.parser().verifyWith(key).build()

  private val mapTypeReference = object : TypeReference<Map<String, Any>>() {}

  override fun generateAccessToken(claims: JwtClaims): String {
    return generateToken(claims, jwtProperties.accessTokenExpiration)
  }

  override fun generateRefreshToken(claims: JwtClaims): String {
    return generateToken(claims, jwtProperties.refreshTokenExpiration)
  }

  override fun generateToken(claims: JwtClaims, expiration: Long): String {
    val (now, expirationDate) = getExpirationDate(expiration)
    val claimsMap = objectMapper.convertValue(claims, mapTypeReference)

    return Jwts.builder()
        .issuedAt(now)
        .expiration(expirationDate)
        .claims(claimsMap)
        .signWith(key)
        .compact()
  }

  override fun verify(token: String): Map<String, Any> {
    val payload = try {
      parser.parseSignedClaims(token).payload
    } catch (ex: Exception) {
      throw InvalidJwtException(ex.message)
    }

    return payload
  }

  private fun getExpirationDate(expiration: Long): Pair<Date, Date> {
    val now = Instant.now()
    return Pair(Date.from(now), Date.from(now.plusSeconds(expiration)))
  }
}