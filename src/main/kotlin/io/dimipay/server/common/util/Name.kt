package io.dimipay.server.common.util

class Name {
  companion object {
    val regularName = Regex("^[ -~ㄱ-ㅎㅏ-ㅣ가-힣]{1,10}$")

    fun toRegularName(name: String, default: () -> String): String {
      return name
          .trim()
          .replace(Regex("[^ -~ㄱ-ㅎㅏ-ㅣ가-힣]"), "")
          .take(10)
          .let {
            it.ifEmpty {
              default()
            }
          }
    }
  }
}