// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.androidx.baselineprofile) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.room) apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    tasks.withType<Test> {
        // Workaround for Windows systems where PATH contains spaces (like "Microsoft VS Code")
        // which can break the Gradle Test Executor command line.
        doFirst {
            systemProperty("java.library.path", ".")
        }
    }
}
}

tasks.register<Copy>("installGitHooks") {
    from(file("$rootDir/config/scripts/pre-commit.sh"))
    into(file("$rootDir/.git/hooks"))
    rename { "pre-commit" }

    doLast {
        println("Git hooks installed successfully to .git/hooks/pre-commit")
    }
}
