plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
    mavenCentral()
}


java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

dependencies {
    implementation("io.spring.gradle:dependency-management-plugin:1.1.0")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.1.0")

}
