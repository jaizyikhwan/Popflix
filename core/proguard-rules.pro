# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn java.lang.invoke.StringConcatFactory

-keep class com.jaizyikhwan.core.di.** { *; }
-keepclassmembers class com.jaizyikhwan.core.di.** { *; }

-keep class com.jaizyikhwan.core.domain.usecase.** { *; }
-keepclassmembers class com.jaizyikhwan.core.domain.usecase.** { *; }

-keep class com.jaizyikhwan.core.data.** { *; }
-keepclassmembers class com.jaizyikhwan.core.data.** { *; }


# Mengabaikan peringatan terkait javax.lang.model.*
-dontwarn javax.lang.model.**

# Menjaga semua kelas di javax.lang.model agar tidak dihapus
-keep class javax.lang.model.** { *; }

# Menambahkan aturan untuk androidx.test agar kompatibel dengan R8 versi terbaru
-keep,allowshrinking class androidx.test.** { <init>(); }

