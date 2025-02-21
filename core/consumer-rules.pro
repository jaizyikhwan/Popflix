# Keep Koin modules
-keep class org.koin.** { *; }
-keep class * extends org.koin.core.module.Module { *; }

# Keep Room Entities and DAOs
-keep class androidx.room.* { *; }
-keep class * extends androidx.room.RoomDatabase { *; }

# Keep Retrofit & Gson
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }

# Keep API Service Interfaces
-keep interface *ApiService { *; }

-keep class net.sqlcipher.** { *; }

# Keep Jetpack Compose
-keep class androidx.compose.** { *; }

# Keep AndroidX Core & Lifecycle
-keep class androidx.lifecycle.** { *; }
-keep class androidx.core.** { *; }

-keep class okhttp3.logging.HttpLoggingInterceptor { *; }

-keep class com.jaizyikhwan.core.BuildConfig { *; }
