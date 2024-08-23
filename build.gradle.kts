import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.expectale"
version = "2.0.0"

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.paperweight)
    alias(libs.plugins.nova)
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.xenondevs.xyz/releases")
}

dependencies {
    paperweight.paperDevBundle(libs.versions.paper)
    implementation(libs.nova)
    compileOnly("xyz.xenondevs.nova:nova-api:0.17-alpha.11")
}

addon {
    id.set("deep_storage")
    name.set("Deep-Storage")
    version.set(project.version.toString())
    novaVersion.set(libs.versions.nova)
    main.set("com.expectale.DeepStorage")
    authors.add("CptBeffHeart")
}

tasks {
    register<Copy>("addonJar") {
        group = "build"
        dependsOn("jar")
        from(File(project.layout.buildDirectory.get().asFile, "libs/${project.name}-${project.version}.jar"))
        into((project.findProperty("outDir") as? String)?.let(::File) ?: project.layout.buildDirectory.get().asFile)
        rename { "${addonMetadata.get().addonName.get()}-${project.version}.jar" }
    }
    
    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
        }
    }
}

afterEvaluate {
    tasks.getByName<Jar>("jar") {
        archiveClassifier = ""
    }
}