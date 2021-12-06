# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/users/aman.gupta/android-sdks/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#-dontwarn android.arch.util.paging.CountedDataSource
#-dontwarn android.arch.persistence.room.paging.LimitOffsetDataSource

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform

# Platform used when running on Java 8 VMs. Will not be used at runtime.

# Retain generic type information for use by reflection by converters and adapters.


# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Annotation
-keep class com.google.** { *; }
-keep class sun.misc.** { *; }
-keep class kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.BuiltInsLoaderImpl
-dontwarn okhttp3.**
#-keep class okhttp3.** { ;}
-keep interface kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoader
-keep class kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoaderImpl
-dontwarn rx.**
-dontwarn javax.annotation.GuardedBy

 -dontwarn javax.annotation.*
 -keep class com.google.gson.examples.android.model.** { *; }
 -dontwarn retrofit2.Platform$Java8
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
#-keep class retrofit2.** { ; }
-keep class com.jafari.farhad.ubazdidcontroller.** { *; }
-dontwarn org.xmlpull.v1.**
-dontwarn

-dontwarn com.squareup.okhttp.**
-keep class org.xmlpull.v1.** { *; }
-keep class android.support.v7.widget.** {*;}
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }
-keepclassmembers class *{
public void *(android.view.View);}
-assumenosideeffects class android.util.Log {
public static *** d(...);
public static *** v(...);
}
-keep class cn.pedant.SweetAlert.Rotate3dAnimation {
    public <init>(...);
 }
-keepattributes Signature
-keeppackagenames org.jsoup.nodes


# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class webkul.opencart.mobikul.Model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
#avoiding the unsafe implementation of hostname verifir
-keepclassmembers class com.paytm.pgsdk.PaytmWebView$PaytmJavaScriptInterface {
public *;
}
-keep class cn.pedant.SweetAlert.Rotate3dAnimation {
public <init>(...);
}
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-keep class webkul.opencart.mobikul.Cart.** {
  public protected private *;
}
-keep class retrofit.** { *; }
-dontwarn retrofit.appengine.UrlFetchClient
-dontwarn retrofit.**
-keep interface com.squareup.okhttp.** { *; }
-keep class com.squareup.okhttp.** { *; }
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }
-dontwarn com.google.gson.Gson$6
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keep public class java.nio. { *; }
-dontwarn
#-keep public class org.codehaus.* { ; }
-dontwarn io.card.**
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.** { *; }
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.**
-dontwarn com.squareup.**
-dontwarn javax.annotation.ParametersAreNonnullByDefault
#For keeping the refleaction response methods
-keepclassmembers class * {
public void * (java.lang.String);
}
-keep class webkul.opencart.mobikul.networkManager.RetrofitCustomCallback**
#-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.GeneratedAppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

-dontwarn okhttp3.internal.platform.*
# ITEXT
 -keep class javax.xml.crypto.dsig.** { *; }
 -dontwarn javax.xml.crypto.dsig.**
 -keep class javax.xml.crypto.** { *; }
 -dontwarn javax.xml.crypto.**

 -keep class org.spongycastle.** { *; }
 -dontwarn org.spongycastle.**

 -dontnote android.net.http.*
 -dontnote org.apache.commons.codec.**
 -dontnote org.apache.http.**
 -keep class com.google.android.gms.** { *; }
 -dontwarn com.google.android.gms.**
#-keepclasseswithmembers class * {
#public void * (java.lang.String, org.ksoap2.SoapEnvelope);
#}