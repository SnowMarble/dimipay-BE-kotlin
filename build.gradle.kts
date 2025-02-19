plugins {
  kotlin("jvm") version "2.1.0"
  kotlin("plugin.spring") version "2.1.0"
  kotlin("plugin.jpa") version "2.1.0"
  id("org.springframework.boot") version "3.4.1"
  id("io.spring.dependency-management") version "1.1.7"
}

group = "io.dimipay"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

repositories {
  mavenCentral()
}

dependencies {
  configurations.all {
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    exclude(group = "commons-logging", module = "commons-logging")
  }

  constraints {
    implementation("com.google.code.gson:gson:2.12.1")
  }

  implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")
  implementation("org.jetbrains.kotlin:kotlin-reflect:2.1.0")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.2")

  implementation("org.springframework.boot:spring-boot-starter-web:3.4.1")
  implementation("org.postgresql:postgresql:42.7.4")

  implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.1")
  implementation("org.springframework.boot:spring-boot-starter-validation:3.4.1")
  implementation("org.springframework.boot:spring-boot-starter-log4j2:3.4.1")
  implementation("org.springframework.boot:spring-boot-starter-aop:3.4.1")

  implementation("org.springframework.boot:spring-boot-starter-security:3.4.1")
  implementation("org.springframework.security:spring-security-crypto:6.4.2")
  implementation("org.bouncycastle:bcprov-jdk18on:1.79")

  implementation("com.google.api-client:google-api-client:2.7.1")
  runtimeOnly("org.aspectj:aspectjweaver:1.9.22")

  implementation("io.jsonwebtoken:jjwt-api:0.12.6")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

  testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.1")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:2.1.0")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.11.4")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

tasks.register<Exec>("db-start") {
  commandLine("docker", "compose", "-f", "./docker/database.docker-compose.yaml", "up", "-d")
}

tasks.register<Exec>("db-status") {
  commandLine("docker", "compose", "-f", "./docker/database.docker-compose.yaml", "ps")
}

tasks.register<Exec>("db-stop") {
  commandLine("docker", "compose", "-f", "./docker/database.docker-compose.yaml", "down")
}

tasks.register<Exec>("db-clean") {
  commandLine("docker", "compose", "-f", "./docker/database.docker-compose.yaml", "down", "-v")
}

tasks.withType<Test> {
  useJUnitPlatform()
}
