plugins {
    kotlin("jvm") version "2.0.21"
}

group = "org.aoc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.michael-bull.kotlin-itertools:kotlin-itertools:1.0.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}