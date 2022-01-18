object LibraryVersionOldModules {
  //other modules
  const val RXJAVA_2 = "2.2.19"
  const val RXJAVA_2_ANDROID = "2.1.1"
  const val RXJAVA_2_INTEROP = "0.13.7"
  const val RXJAVA = "1.2.7"
  const val RXJAVA_ANDROID = "1.2.1"
  const val RX_LINT = "1.2"
  const val RXJAVA_PROGUARD_RULES = "1.2.7.0"
  const val APP_COMPAT = "1.1.0"
  const val RX_RELAY = "1.2.0"
  const val PARCELER = "1.1.12"
  const val FLURRY = "12.1.0"
  const val RAKAM = "2.7.14"
  const val AMPLITUDE = "2.34.1"
  const val INDICATIVE = "1.0.1"
  const val COROUTINES = "1.3.7"
  const val ROOM = "2.2.4"
  const val GSON = "2.8.2"
  const val STATE_MACHINE = "0.1.2"
  const val CORE_KTX = "1.2.0"
  const val CONSTRAINT_LAYOUT = "2.0.4"
  const val MATERIAL = "1.2.0-beta01"
  const val JW_RX_BINDING = "1.0.0"
  const val EPOXY = "3.8.0"
  const val PLAY_SERVICES_BASEMENT = "16.1.0"
  const val RETROFIT = "2.1.0"
  const val JACKSON = "2.8.5"
  const val OK_HTTP = "3.12.3"
  const val FILE_DOWNLOADER = "1.4.1"
  const val FILE_DOWNLOADER_OK_HTTP = "1.0.0"
}

private object LibraryVersion {
  //main modules
  const val CORE_KTX = "1.2.0"
  const val APP_COMPAT = "1.3.0"
  const val MATERIAL = "1.4.0"
  const val CONSTRAINT_LAYOUT = "2.1.2"
  const val RETROFIT = "2.9.0"
  const val RETROFIT_MOSHI_CONVERTER = "2.9.0"
  const val OK_HTTP = "4.2.2"
  const val TIMBER = "5.0.1"
  const val LOTTIE = "2.7.0"
  const val FRAGMENT_KTX = "1.4.0"
  const val LIFECYCLE = "1.1.1"
  const val COIL = "1.0.0"
  const val ROOM = "2.4.0"
}

object LibraryDependency {
  const val CORE_KTX = "androidx.core:core-ktx:${LibraryVersion.CORE_KTX}"
  const val APP_COMPAT = "androidx.appcompat:appcompat:${LibraryVersion.APP_COMPAT}"
  const val MATERIAL = "com.google.android.material:material:${LibraryVersion.MATERIAL}"
  const val CONSTRAINT_LAYOUT =
      "androidx.constraintlayout:constraintlayout:${LibraryVersion.CONSTRAINT_LAYOUT}"
  const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${CoreVersion.KOTLIN}"
  const val RETROFIT = "com.squareup.retrofit2:retrofit:${LibraryVersion.RETROFIT}"
  const val RETROFIT_MOSHI_CONVERTER =
      "com.squareup.retrofit2:converter-moshi:${LibraryVersion.RETROFIT_MOSHI_CONVERTER}"
  const val OK_HTTP = "com.squareup.okhttp3:okhttp:${LibraryVersion.OK_HTTP}"
  const val LOGGING_INTERCEPTOR =
      "com.squareup.okhttp3:logging-interceptor:${LibraryVersion.OK_HTTP}"
  const val TIMBER = "com.jakewharton.timber:timber:${LibraryVersion.TIMBER}"
  const val COROUTINES =
      "org.jetbrains.kotlinx:kotlinx-coroutines-android:${CoreVersion.COROUTINES}"
  const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${LibraryVersion.FRAGMENT_KTX}"
  const val LIFECYCLE_EXTENSIONS = "android.arch.lifecycle:extensions:${LibraryVersion.LIFECYCLE}"
  const val LIFECYCLE_VIEW_MODEL_KTX =
      "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibraryVersion.LIFECYCLE}"
  const val NAVIGATION_FRAGMENT_KTX =
      "androidx.navigation:navigation-fragment-ktx:${CoreVersion.NAVIGATION}"
  const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${CoreVersion.NAVIGATION}"
  const val COIL = "io.coil-kt:coil:${LibraryVersion.COIL}"
  const val LOTTIE = "com.airbnb.android:lottie:${LibraryVersion.LOTTIE}"
  const val ROOM = "androidx.room:room-runtime:${LibraryVersion.ROOM}"
  const val ROOM_COMPILER = "androidx.room:room-compiler:${LibraryVersion.ROOM}"
}