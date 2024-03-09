package xyz.moevm.ecology.api

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.moevm.ecology.api.services.EcologyAuthApiService
import xyz.moevm.ecology.data.stores.CookiesViewModel

const val SERVER_URL = "https://vladdoth.xyz/ecology/api/"

class ApiViewModel(application: Application) : AndroidViewModel(application) {
    private val cookiesVM = CookiesViewModel(application)
    private val cookieJar = DataStoreCookieJar(cookiesVM)

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient()
                .newBuilder()
                .cookieJar(cookieJar)
                .build()
        )
        .baseUrl(SERVER_URL)
        .build()

    val auth: EcologyAuthApiService by lazy {
        retrofit.create(EcologyAuthApiService::class.java)
    }
}
