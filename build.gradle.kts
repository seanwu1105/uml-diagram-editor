plugins {
    java
    jacoco
    application
    id("org.openjfx.javafxplugin") version "0.0.8"
}

group = "io.github.seanwu1105"
version = "1.0"

tasks {
    test {
        useJUnitPlatform()
    }

    jacocoTestReport {
        reports {
            xml.isEnabled = true
            html.isEnabled = false
        }
    }
}

repositories {
    mavenCentral()
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

application {
    mainClassName = "io.github.seanwu1105.umldiagrameditor.Main"
}

javafx {
    version = "11"
    modules("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.jetbrains", "annotations", "19.0.0")

    testImplementation("org.junit.jupiter", "junit-jupiter", "5.6.2")
    testImplementation("org.testfx", "testfx-core", "4.0.16-alpha")
    testImplementation("org.testfx", "testfx-junit5", "4.0.16-alpha")
    testImplementation("org.hamcrest", "hamcrest", "2.2")
    testImplementation("org.testfx", "openjfx-monocle", "jdk-11+26")
}