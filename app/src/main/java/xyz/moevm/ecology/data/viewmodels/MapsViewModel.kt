package xyz.moevm.ecology.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.moevm.ecology.api.ApiViewModel
import xyz.moevm.ecology.api.types.MapInfo


class MapsViewModel(application: Application) : AndroidViewModel(application) {
    private val api = ApiViewModel(application)

    private val mapsListState = MutableStateFlow<List<MapInfo>>(emptyList())
    val mapsList = mapsListState.asStateFlow()

    fun fetchMaps()= viewModelScope.launch {
        var respMapsListState: List<MapInfo> = emptyList()

        kotlin.runCatching {
            respMapsListState = api.maps.getMapsList().body()?: emptyList()
        }.onSuccess {
            mapsListState.update { respMapsListState }
        }.onFailure{
            // Можно что-то сделать с ошибкой, но логи тоже ничего )
            it.printStackTrace()
        }
    }

}