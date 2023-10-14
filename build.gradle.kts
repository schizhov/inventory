plugins {
    id("java")
}

group = "ai.dm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val dropwizardVersion = "4.0.2"
val slf4jVersion = "2.0.9"
val postgresqlVersion = "42.6.0"
val junitVersion = "5.9.2"
val testcontainersVersion = "1.19.1"

dependencies {
    implementation(platform("io.dropwizard:dropwizard-bom:$dropwizardVersion"))

    implementation("io.dropwizard:dropwizard-core") {
        exclude("com.google.code.findbugs")
    }

    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}