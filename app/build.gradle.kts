plugins {
    id(AppConfig.androidAppPluginId)
    id(AppConfig.kotlinAndroid)
    kotlin("kapt")
    id(AppConfig.daggerHiltPlugin)
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.appId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode =  AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner =  AppConfig.androidJunitRunner
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), AppConfig.proGuardRules)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }
    buildFeatures{
        viewBinding = true
    }
}
kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(KotlinDependencies.coreKtxDep)
    implementation(AndroidDependencies.appCompatDep)
    implementation(AndroidDependencies.fragmentKtxDep)
    implementation(AndroidDependencies.materialDep)
    implementation(AndroidDependencies.constraintLayoutDep)
    implementation(AndroidDependencies.lifeCycleRuntimeKtxDep)
    implementation(AndroidDependencies.viewModelDep)
    implementation(AndroidDependencies.liveDataDep)
    implementation(AndroidDependencies.daggerHiltRuntimeDep)
    kapt(AndroidDependencies.daggerHiltCompileTimeDep)
    implementation(AndroidDependencies.roomRuntimeDep)
    kapt(AndroidDependencies.roomCompileTimeDep)
    implementation(ThirdPartyDependencies.glideRuntimeDep)
    kapt(ThirdPartyDependencies.glideCompileTimeDep)
    implementation(ThirdPartyDependencies.rxJavaDep)
    implementation(ThirdPartyDependencies.rxAndroidDep)
    testImplementation(UnitTestDependencies.junitDep)
    androidTestImplementation(UnitTestDependencies.junitExtDep)
    androidTestImplementation(UnitTestDependencies.espressoDep)
}