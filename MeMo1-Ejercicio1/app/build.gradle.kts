plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.junit.vintage:junit-vintage-engine:5.9.1")

    testImplementation ("io.cucumber:cucumber-java:7.4.1")
    testImplementation ("io.cucumber:cucumber-junit:7.4.1")

    implementation("com.google.guava:guava:31.1-jre")
}

application {
    mainClass.set("memo1.ejercicio1.Main")
}

tasks.register<Test>("cucumberTest") {
    useJUnitPlatform()

    include("**/CucumberTest*")

    testLogging {
        events("failed")
        showStandardStreams = false
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()

    exclude("**/CucumberTest*")

    testLogging {
        events("failed")
        showStandardStreams = false
    }
}
