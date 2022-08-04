// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(ProjectGradleDependencies.buildGradlePluginDep)
        classpath(ProjectGradleDependencies.kotlinGradlePluginDep)
        classpath(ProjectGradleDependencies.hiltGradlePluginDep)
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}