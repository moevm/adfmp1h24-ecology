package xyz.moevm.ecology.api

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.moevm.ecology.api.services.EcologyAuthApiService
import xyz.moevm.ecology.api.services.EcologyMapApiService
import xyz.moevm.ecology.api.services.EcologyMapsApiService
import xyz.moevm.ecology.api.services.EcologyObjectsApiService
import xyz.moevm.ecology.api.services.EcologyUsersApiService
import xyz.moevm.ecology.data.stores.CookiesViewModel

const val SERVER_URL = "https://vladdoth.xyz/ecology/api/"

class ApiViewModel(application: Application) : AndroidViewModel(application) {
    private val cookiesVM = CookiesViewModel(application)
    private val cookieJar = DataStoreCookieJar(cookiesVM)

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
    private val okHttp = OkHttpClient().newBuilder().cookieJar(cookieJar)
        .addInterceptor((logger)) // Логи запросов

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
        .baseUrl(SERVER_URL)
        .build()

    val auth: EcologyAuthApiService by lazy {
        retrofit.create(EcologyAuthApiService::class.java)
    }

    val map: EcologyMapApiService by lazy {
        retrofit.create(EcologyMapApiService::class.java)
    }

    val users: EcologyUsersApiService by lazy {
        retrofit.create(EcologyUsersApiService::class.java)
    }

    val objects: EcologyObjectsApiService by lazy {
        retrofit.create(EcologyObjectsApiService::class.java)
    }

    val maps: EcologyMapsApiService by lazy {
        retrofit.create(EcologyMapsApiService::class.java)
    }
}
