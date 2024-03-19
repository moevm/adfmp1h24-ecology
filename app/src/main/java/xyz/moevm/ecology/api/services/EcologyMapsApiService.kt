package xyz.moevm.ecology.api.services

import retrofit2.Response
import retrofit2.http.GET
import xyz.moevm.ecology.api.types.MapInfo


interface EcologyMapsApiService {
    @GET("images/")
    suspend fun getMapsList(): Response<List<MapInfo>>
}