package xyz.moevm.ecology.api.services

import retrofit2.Response
import retrofit2.http.GET
import xyz.moevm.ecology.api.types.ServerUserData

interface EcologyAuthApiService {
    @GET("auth/login/dev")
    suspend fun devLogin(): Response<ServerUserData>

    @GET("auth/login")
    suspend fun login(): Response<ServerUserData>
}