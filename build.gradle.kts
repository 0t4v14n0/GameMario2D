plugins {
    id("java")
}

group = "com"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(platform("org.lwjgl:lwjgl-bom:3.3.4"))
    implementation("org.lwjgl:lwjgl")
    implementation("org.lwjgl:lwjgl-assimp")
    implementation("org.lwjgl:lwjgl-glfw")
    implementation("org.lwjgl:lwjgl-nfd")
    implementation("org.lwjgl:lwjgl-openal")
    implementation("org.lwjgl:lwjgl-opengl")
    implementation("org.lwjgl:lwjgl-stb")
    runtimeOnly("org.lwjgl:lwjgl::natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-assimp::natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-glfw::natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-nfd::natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-openal::natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-opengl::natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-stb::natives-windows")
    implementation("org.joml:joml:1.10.7")
}

tasks.test {
    useJUnitPlatform()
}