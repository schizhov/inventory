repositories {
    mavenCentral()
}

plugins {
    id("java")
    id("com.google.cloud.tools.jib") version "3.3.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

group = "ai.dm"
version = "1.0-SNAPSHOT"


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

    implementation("io.dropwizard:dropwizard-jdbi3")

    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

jib {
    from {
        image = "eclipse-temurin:17-jre"
    }

    to {
        image = "inventory-service"
        tags = setOf("${project.version}", "latest")
    }

    container {
        jvmFlags = arrayListOf("-XshowSettings:all")
        args = arrayListOf("server", "/config.yaml")
        ports = arrayListOf("8080", "8088")
        labels.putAll(mapOf("version" to "${project.version}"))
        format = com.google.cloud.tools.jib.api.buildplan.ImageFormat.OCI
    }
}