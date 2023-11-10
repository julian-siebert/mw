plugins {
    application
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

application {
    mainClass.set("programm.Main")
}

group = "de.juliansiebert"
version = "1.0"

repositories {
    mavenCentral()
}

val log4jVersion = "2.20.0"

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.43.2.2")

    implementation("org.apache.logging.log4j:log4j-api:${log4jVersion}")
    implementation("org.apache.logging.log4j:log4j-core:${log4jVersion}")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:${log4jVersion}")
    implementation("org.apache.logging.log4j:log4j-iostreams:${log4jVersion}")

    implementation("fastutil:fastutil:5.0.9")

}

tasks {
    jar {
        manifest {
            attributes["Multi-Release"] = "true"
        }
    }
}