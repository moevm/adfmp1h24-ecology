package xyz.moevm.ecology.api.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import xyz.moevm.ecology.api.types.ServerAuthData
import xyz.moevm.ecology.api.types.ServerUserData

interface EcologyAuthApiService {
    @GET("auth/login/dev")
    suspend fun devLogin(): Response<ServerUserData>

    @GET("auth/login")
    suspend fun getLogin(): Response<ServerUserData>

    @DELETE("auth/login")
    suspend fun logout(): Response<String>

    @POST("auth/login")
    suspend fun login(@Body body: ServerAuthData): Response<ServerUserData>
}