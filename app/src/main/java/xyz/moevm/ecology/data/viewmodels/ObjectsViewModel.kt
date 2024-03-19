package xyz.moevm.ecology.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.moevm.ecology.api.ApiViewModel
import xyz.moevm.ecology.api.types.ObjectInfo

class ObjectsViewModel(application: Application) : AndroidViewModel(application) {
    private val api = ApiViewModel(application)

    private val objectsListState = MutableStateFlow<List<ObjectInfo>>(emptyList())
    val objectsList = objectsListState.asStateFlow()

    fun fetchObjects()= viewModelScope.launch {
        var respObjectsListState: List<ObjectInfo> = emptyList()

        kotlin.runCatching {
            respObjectsListState = api.objects.getObjectsList().body()?: emptyList()
        }.onSuccess {
            objectsListState.update { respObjectsListState }
        }.onFailure{
            // Можно что-то сделать с ошибкой, но логи тоже ничего )
            it.printStackTrace()
        }
    }

}