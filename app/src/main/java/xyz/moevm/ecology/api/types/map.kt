package xyz.moevm.ecology.api.types

import com.google.android.gms.maps.model.LatLng


data class MapInfo(
    val id: String,
    val name: String,
    val updateUserId: String,
    val updateDatetime: String,
    val center: List<Double>,
    val coordinates: List<List<Double>>,
    val ready: Boolean,
    val sliced: Boolean
)

data class ObjectInfo(
    val id: String,
    val type: String,
    val name: String,
    val color: String,
    val updateUserId: String,
    val updateDatetime: String,
    val center: List<Double>,
    val coordinates: List<List<Double>>
)

data class SendObjectInfo(
    val type: String,
    val name: String,
    val color: String,
    val updateUserId: String,
    val updateDatetime: String,
    val center: List<Double>,
    val coordinates: List<List<Double>>
)

data class ObjectsChangeInfo(
    val created: List<SendObjectInfo>,
    val deleted: List<SendObjectInfo>,
    val edited: List<SendObjectInfo>
)