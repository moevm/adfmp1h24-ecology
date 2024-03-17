package xyz.moevm.ecology.api.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import xyz.moevm.ecology.api.types.ObjectInfo
import xyz.moevm.ecology.api.types.MapInfo
import xyz.moevm.ecology.api.types.ObjectsChangeInfo

interface EcologyMapApiService {
    @GET("/ecology/api/objects/near/{y}/{x}/{r}")
    suspend fun getObjectsNear(@Path("y") y: Double,
                               @Path("x") x: Double,
                               @Path("r") r: Double): Response<List<ObjectInfo>>

    @GET("/ecology/api/images/near/{y}/{x}/{r}")
    suspend fun getMapsNear(@Path("y") y: Double,
                            @Path("x") x: Double,
                            @Path("r") r: Double): Response<List<MapInfo>>

    @POST("/ecology/api/objects/update")
    suspend fun updateObjects(@Body body: ObjectsChangeInfo): Response<String>
}