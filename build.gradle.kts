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

tasks.withType<KotlinCompile> {
    sourceCompatibility = JavaVersion.VERSION_1_8.name
    targetCompatibility = JavaVersion.VERSION_1_8.name
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=scrict")
        jvmTarget= "1.8"
    }
}

tasks {
    "run"(JavaExec::class) {
        loadEnvVars(this,".env")
    }
}

fun loadEnvVars(task: JavaExec,envPath: String) {
    file(envPath).readLines().forEach {
        val envVar = it.split("=", limit=2)
        if (envVar.size > 1) {
            print("my var is " + envVar[0] + envVar[1])
            task.environment(envVar[0], envVar[1])
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.koin:koin-core:2.1.6")
    implementation("io.javalin:javalin:3.9.1")
    implementation("org.joda:joda-money:1.0.1")
    implementation("com.github.guepardoapps:kulid:1.1.2.0")
    implementation("org.litote.kmongo:kmongo:4.0.3")
    implementation("com.natpryce:konfig:1.6.10.0")
    implementation("org.slf4j:slf4j-api:2.0.0-alpha1")
    implementation("org.apache.logging.log4j:log4j-slf4j18-impl:2.13.0")
}
