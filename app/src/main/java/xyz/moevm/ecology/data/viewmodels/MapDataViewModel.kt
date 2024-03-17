package xyz.moevm.ecology.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.moevm.ecology.api.ApiViewModel
import xyz.moevm.ecology.api.types.CallState
import xyz.moevm.ecology.api.types.MapInfo
import xyz.moevm.ecology.api.types.ObjectInfo
import xyz.moevm.ecology.api.types.ObjectsChangeInfo
import xyz.moevm.ecology.api.types.SendObjectInfo

class MapDataViewModel(application: Application) : AndroidViewModel(application) {
    private val api = ApiViewModel(application)

    private val _objects = MutableStateFlow<List<ObjectInfo>?>(null)
    val objects: StateFlow<List<ObjectInfo>?> = _objects.asStateFlow()

    private val _maps = MutableStateFlow<List<MapInfo>?>(null)
    val maps: StateFlow<List<MapInfo>?> = _maps.asStateFlow()

    private val _cameraPos = MutableStateFlow<LatLng>(LatLng(59.93863, 30.31413))
    val cameraPos: StateFlow<LatLng> = _cameraPos.asStateFlow()

    private val _lastAddedObject = MutableStateFlow<SendObjectInfo?>(null)
    val lastAddedObject: StateFlow<SendObjectInfo?> = _lastAddedObject.asStateFlow()

    private val _lastCallState = MutableStateFlow<CallState>(CallState.WAITING)
    val lastCallState: StateFlow<CallState> = _lastCallState.asStateFlow()

    private val _userGeoPermissionAsked = MutableStateFlow<Boolean>(false)
    val userGeoPermissionAsked: StateFlow<Boolean> = _userGeoPermissionAsked.asStateFlow()

    private fun setObjects(objectsList: List<ObjectInfo>?) {
        _objects.update { objectsList }
    }

    private fun setMaps(mapsList: List<MapInfo>?) {
        _maps.update { mapsList }
    }

    fun setCameraPos(newCameraPos: LatLng) {
        _cameraPos.update { newCameraPos }
    }

    fun setObject(newAddedObject: SendObjectInfo?) {
        _lastAddedObject.update { newAddedObject }
    }

    private fun setCallState(callState: CallState) {
        _lastCallState.update { callState }
    }

    fun setUserGeoPermissionAsked(asked: Boolean) {
        _userGeoPermissionAsked.update { asked }
    }


    fun getMapsAndObjectsNear(y: Double, x: Double, r: Double) = viewModelScope.launch {
        var respMaps: List<MapInfo>? = null
        var respObjects: List<ObjectInfo>? = null

        kotlin.runCatching {
            respMaps = api.map.getMapsNear(y, x, r).body()
            respObjects = api.map.getObjectsNear(y, x, r).body()
        }.onSuccess {
            setMaps(respMaps)
            setObjects(respObjects)
        }.onFailure{
            // Можно что-то сделать с ошибкой, но логи тоже ничего )
            it.printStackTrace()
        }
    }

    fun updateObjects(createdObj: SendObjectInfo) = viewModelScope.launch {
        val objChangeInfo = ObjectsChangeInfo(listOf(createdObj), listOf(), listOf())

        kotlin.runCatching {
            setCallState(CallState.WAITING)
            api.map.updateObjects(objChangeInfo)
        }.onSuccess {
            setObject(createdObj)
            setCallState(CallState.SUCCESS)
        }.onFailure{
            // Можно что-то сделать с ошибкой, но логи тоже ничего )
            setCallState(CallState.ERROR)
            it.printStackTrace()
        }
    }
}