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
  implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.1")
  implementation("org.springframework.boot:spring-boot-starter-web:3.4.1")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.2")
  implementation("org.jetbrains.kotlin:kotlin-reflect:2.1.0")
  implementation("org.postgresql:postgresql:42.7.4")
  compileOnly("org.projectlombok:lombok:1.18.36")

  implementation("org.springframework.security:spring-security-crypto:6.4.2")
  implementation("org.bouncycastle:bcprov-jdk18on:1.79")

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
  group = "db"
  description = "Starts the database"

  workingDir("${project.projectDir}/docker")
  commandLine("docker-compose", "-f", "database.docker-compose.yaml", "up", "-d")
}

tasks.register<Exec>("db-status") {
  group = "db"
  description = "Checks the status of the database"

  workingDir("${project.projectDir}/docker")
  commandLine("docker-compose", "-f", "database.docker-compose.yaml", "ps")
}

tasks.register<Exec>("db-stop") {
  group = "db"
  description = "Stops the database"

  workingDir("${project.projectDir}/docker")
  commandLine("docker-compose", "-f", "database.docker-compose.yaml", "down")
}

tasks.register<Exec>("db-clean") {
  group = "db"
  description = "Cleans the database"

  workingDir("${project.projectDir}/docker")
  commandLine("docker-compose", "-f", "database.docker-compose.yaml", "down", "-v")
}

tasks.withType<Test> {
  useJUnitPlatform()
}
