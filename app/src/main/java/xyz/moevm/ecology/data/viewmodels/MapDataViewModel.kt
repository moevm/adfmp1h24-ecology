package xyz.moevm.ecology.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.moevm.ecology.api.ApiViewModel
import xyz.moevm.ecology.api.types.MapInfo
import xyz.moevm.ecology.api.types.ObjectInfo

class MapDataViewModel(application: Application) : AndroidViewModel(application) {
    private val api = ApiViewModel(application)

    private val _cameraPos = MutableStateFlow<LatLng>(LatLng(59.93863, 30.31413))
    val cameraPos: StateFlow<LatLng> = _cameraPos.asStateFlow()

    private val _objects = MutableStateFlow<List<ObjectInfo>?>(null)
    val objects: StateFlow<List<ObjectInfo>?> = _objects.asStateFlow()

    private val _maps = MutableStateFlow<List<MapInfo>?>(null)
    val maps: StateFlow<List<MapInfo>?> = _maps.asStateFlow()

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
}