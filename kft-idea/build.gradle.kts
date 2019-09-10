
plugins {
  kotlin("jvm") version "1.3.41"
}

val kotlinpoetVersion = "1.0.1"

repositories {
  jcenter()
  maven("https://dl.bintray.com/hexlabsio/kloudformation")
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation("io.kloudformation:kloudformation:1.1.2")
  implementation("io.hexlabs:kloudformation-serverless-module:1.1.1")
  implementation(group = "com.squareup", name = "kotlinpoet", version = kotlinpoetVersion)
  testImplementation(group = "org.jetbrains.kotlin", name = "kotlin-test-junit5", version = "1.3.21")
  testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "1.3.21")
  testRuntime(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = "5.0.0")
}


