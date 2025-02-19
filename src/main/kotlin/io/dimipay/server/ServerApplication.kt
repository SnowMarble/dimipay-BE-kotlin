package io.dimipay.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
class ServerApplication

fun main(args: Array<String>) {
  runApplication<ServerApplication>(*args)
}
