package xyz.moevm.ecology.api

import retrofit2.http.GET

interface EcologyAuthApiService {
    @GET("auth/login/dev")
    suspend fun devLogin(): ServerUserData

    @GET("auth/login")
    suspend fun login(): ServerUserData
}