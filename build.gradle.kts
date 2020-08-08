import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val mainClass = "com.checkingaccount.Main"
group = "com.checkingaccount"
version = "1.0.0-SNAPSHOT"


plugins {
    application
    kotlin("jvm") version("1.3.72")
}

application {
    mainClassName = mainClass
}

repositories {
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.koin:koin-core:2.1.6")
    implementation("io.javalin:javalin:3.9.1")
    implementation("org.joda:joda-money:1.0.1")
    implementation("com.github.guepardoapps:kulid:1.1.2.0")
    implementation("org.litote.kmongo:kmongo:4.0.3")
    implementation("com.natpryce:konfig:1.6.10.0")
}

