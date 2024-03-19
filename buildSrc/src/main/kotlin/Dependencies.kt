object Dependencies {
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltKapt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"

    const val firebasePlatform = "com.google.firebase:firebase-bom:${Versions.firebase}"
    const val firebaseDatabase = "com.google.firebase:firebase-database-ktx"

    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    const val javaxInject = "javax.inject:javax.inject:1"

    const val javapoet = "com.squareup:javapoet:${Versions.javapoet}"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    const val googleAgp = "com.google.gms:google-services:${Versions.google}"
}