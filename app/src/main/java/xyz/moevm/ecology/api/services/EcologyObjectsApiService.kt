package xyz.moevm.ecology.api.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import xyz.moevm.ecology.api.types.ObjectInfo

interface EcologyObjectsApiService {
    @GET("objects/")
    suspend fun getObjectsList(): Response<List<ObjectInfo>>


    @GET("objects/{id}")
    suspend fun getObject(
        @Path("id") id: String,
    ): Response<ObjectInfo>
}