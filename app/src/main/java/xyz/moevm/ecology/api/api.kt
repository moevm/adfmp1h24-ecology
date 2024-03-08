package xyz.moevm.ecology.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.moevm.ecology.api.services.EcologyAuthApiService


const val SERVER_URL = "https://vladdoth.xyz/ecology/api/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .client(
        OkHttpClient()
            .newBuilder()
            .cookieJar(SessionCookieJar())
            .build()
    )
    .baseUrl(SERVER_URL)
    .build()


object EcologyApi {
    val auth: EcologyAuthApiService by lazy {
        retrofit.create(EcologyAuthApiService::class.java)
    }
}