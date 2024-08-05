buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.8.22")
    }
}

plugins {
    id("com.android.application") version "8.5.0" apply false
    id("com.android.library") version "8.5.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.22" apply false
}