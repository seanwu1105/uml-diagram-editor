plugins {
    java
    jacoco
}

group = "io.github.seanwu1105"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains", "annotations", "19.0.0")

    testImplementation("org.junit.jupiter", "junit-jupiter", "5.6.2")
}

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

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}