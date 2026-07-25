# Firebase Crashlytics
-keepattributes SourceFile,LineNumberTable
-keep public class com.google.firebase.crashlytics.** { *; }

# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleAnnotations, RuntimeInvisibleParameterAnnotations
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-dontwarn retrofit2.**

# OkHttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# Gson (converter-gson uses it)
-keep class com.google.gson.** { *; }
-keep class com.android.knewsapp.news.domain.model.** { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-keep class androidx.room.** { *; }

# Hilt
-keep class dagger.hilt.** { *; }
-keep interface dagger.hilt.** { *; }
-dontwarn dagger.hilt.**

# Security Module (Protecting our cryptographic logic)
-keep class com.android.knewsapp.security.** { *; }
-keep class com.auth0.jwt.** { *; }
