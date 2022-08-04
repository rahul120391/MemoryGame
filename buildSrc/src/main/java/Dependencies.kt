object KotlinDependencies{
     const val coreKtxDep = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
}

object AndroidDependencies{
    const val appCompatDep = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val materialDep = "com.google.android.material:material:${Versions.materialVersion}"
    const val constraintLayoutDep = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val fragmentKtxDep = "androidx.fragment:fragment-ktx:${Versions.fragmentKtxVersion}"
    const val lifeCycleRuntimeKtxDep = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"
    const val viewModelDep = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    const val liveDataDep = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    const val roomRuntimeDep = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompileTimeDep = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val daggerHiltRuntimeDep = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val daggerHiltCompileTimeDep = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
}

object ThirdPartyDependencies{
      const val glideRuntimeDep = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
      const val glideCompileTimeDep = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"
      const val rxJavaDep = "io.reactivex.rxjava3:rxjava:${Versions.rxJavaVersion}"
      const val rxAndroidDep = "io.reactivex.rxjava3:rxandroid:${Versions.rxJavaVersion}"
}

object UnitTestDependencies{
     const val junitDep = "junit:junit:${Versions.junitVersion}"
     const val junitExtDep = "androidx.test.ext:junit:${Versions.junitExtVersion}"
     const val espressoDep = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
}

object ProjectGradleDependencies{
     const val buildGradlePluginDep = "com.android.tools.build:gradle:${Versions.gradleVersion}"
     const val kotlinGradlePluginDep = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
     const val hiltGradlePluginDep = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}"
}