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

# Keep javax.lang.model.element classes
-keep class javax.lang.model.** { *; }

# Keep errorprone annotations
-keep class com.google.errorprone.annotations.** { *; }

# Keep Jetpack Compose related classes
-keep class androidx.compose.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.activity.** { *; }

# Keep Composable functions
-keep @androidx.compose.runtime.Composable class * { *; }

# Keep ViewModels
-keep class * extends androidx.lifecycle.ViewModel { *; }
