plugins {
    java
    `java-library`
    jacoco
    idea
    id("io.spring.dependency-management")
    id("org.springframework.boot")
}

group = "org.example"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

idea.module {

    isDownloadSources = true
    isDownloadJavadoc = true
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    testCompileOnly("org.projectlombok:lombok:1.18.28")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.28")

    implementation("gradle.plugin.com.github.spotbugs.snom:spotbugs-gradle-plugin:4.7.5")
    implementation("org.springframework:spring-web:5.3.24")
    implementation("org.springframework:spring-webmvc:5.3.24")
    implementation("org.apache.poi:poi-ooxml-full:5.2.3")
    implementation("org.apache.poi:poi:5.2.3")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation(platform("com.fasterxml.jackson:jackson-bom:2.14.0"))
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.named<Test>("test") {
    testLogging {
        events("started", "skipped", "failed", "passed")
        setExceptionFormat("short")
    }
}

