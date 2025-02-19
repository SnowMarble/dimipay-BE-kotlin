package io.dimipay.server.user.infrastructure.service.googleLoginServiceImpl

import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "google")
data class GoogleProperties(
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
    val audiences: MutableSet<String>,
) {

  val transport: HttpTransport = NetHttpTransport()
  val gsonFactory: JsonFactory = GsonFactory.getDefaultInstance()

  init {
    audiences.add(clientId)
  }
}
