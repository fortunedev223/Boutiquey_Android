package webkul.opencart.mobikul.networkManager

import android.content.Context
import android.util.Log
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.utils.AppSharedPreference

object ApiClient {
    private var retrofit: Retrofit? = null
    private var retrofit1: Retrofit? = null
    private var prevToken: String? = null

    fun getClient(context: Context): Retrofit? {
        if (retrofit == null || prevToken == null || !prevToken!!.equals(
                        AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME), ignoreCase = true)) {
            try {
                SSLContext.getInstance("TLSv1.2")
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

            try {
                ProviderInstaller.installIfNeeded(context.applicationContext)
            } catch (e: GooglePlayServicesRepairableException) {
                e.printStackTrace()
            } catch (e: GooglePlayServicesNotAvailableException) {
                e.printStackTrace()
            }
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val oktHttpClient = OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.MINUTES)
                    .writeTimeout(15, TimeUnit.MINUTES)
                    .readTimeout(15, TimeUnit.MINUTES)
            oktHttpClient.addInterceptor(logging)
            if (AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME) != "1") {
                oktHttpClient.addInterceptor { chain ->
                    Log.d("Intercepter", "getClient: " + AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
                    val original = chain.request()
                    val request = original.newBuilder()
                            .header("wk_token", AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
                            .header("Wk-Token", AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
                            .method(original.method(), original.body())
                            .build()
                    chain.proceed(request)
                }
            }
            prevToken = AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME)
            retrofit = Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(oktHttpClient.build())
                    .build()
        }
        return this.retrofit
    }

    fun getClientNew(context: Context): Retrofit? {
        if (retrofit1 == null) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val oktHttpClient = OkHttpClient.Builder()
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
            oktHttpClient.addInterceptor(logging)
            retrofit1 = Retrofit.Builder()
                    .baseUrl("https://www.paytabs.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(oktHttpClient.build())
                    .build()
        }
        return retrofit1
    }
}
