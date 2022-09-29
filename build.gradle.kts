import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.lang.Runtime.version

plugins {
    kotlin("jvm") version "1.7.10"
    id("org.openjfx.javafxplugin") version "0.0.13"
    application
}

javafx {
    version = "11.0.2"
    modules("javafx.controls", "javafx.fxml" , "javafx.graphics")
}

group = "org.example"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation ("io.github.microutils:kotlin-logging:2.1.23")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("mysql:mysql-connector-java:8.0.30")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}


application {
    mainClass.set("org.patryk.rally.app.console.main.Main")
}