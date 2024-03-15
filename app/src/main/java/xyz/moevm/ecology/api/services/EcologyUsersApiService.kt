package xyz.moevm.ecology.api.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import xyz.moevm.ecology.api.types.ServerUserData
import xyz.moevm.ecology.api.types.ServerUserEditData

interface EcologyUsersApiService {
    @GET("users/")
    suspend fun getUsersList(): Response<List<ServerUserData>>

    @GET("users/user/{id}")
    suspend fun getUser(
        @Path("id") id: String,
    ): Response<ServerUserData>

    @PUT("users/user/{id}")
    suspend fun editUser(
        @Path("id") id: String,
        @Body body: ServerUserEditData
    ): Response<String>

    @PUT("users/user/self")
    suspend fun editSelf(@Body body: ServerUserEditData): Response<String>
}