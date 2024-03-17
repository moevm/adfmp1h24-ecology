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

    private val _objectAddCallState = MutableStateFlow<CallState>(CallState.WAITING)
    val objectAddCallState: StateFlow<CallState> = _objectAddCallState.asStateFlow()

    private fun setObjects(objectsList: List<ObjectInfo>?) {
        _objects.update { objectsList }
    }

    private fun setMaps(mapsList: List<MapInfo>?) {
        _maps.update { mapsList }
    }

    fun setCameraPos(newCameraPos: LatLng) {
        _cameraPos.update { newCameraPos }
    }

    fun getMapsAndObjectsNear(y: Double, x: Double, r: Double) = viewModelScope.launch {
        setMaps(api.map.getMapsNear(y, x, r).body())
        setObjects(api.map.getObjectsNear(y, x, r).body())
    }

    fun updateObjects(createdObj: SendObjectInfo) = viewModelScope.launch {
        _objectAddCallState.update { CallState.WAITING }
        val objChangeInfo = ObjectsChangeInfo(listOf(createdObj), listOf(), listOf())

        val result = async { api.map.updateObjects(objChangeInfo) }.await()
        if (result.isSuccessful) {
            _objectAddCallState.update { CallState.SUCCESS }
        } else {
            _objectAddCallState.update { CallState.ERROR }
        }
    }
}