package xyz.moevm.ecology.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.moevm.ecology.api.ApiViewModel
import xyz.moevm.ecology.api.types.ServerUserData
import xyz.moevm.ecology.data.stores.KarmaMockViewModel

class UsersViewModel(application: Application) : AndroidViewModel(application) {
    private val api = ApiViewModel(application)
    private val karmaMock = KarmaMockViewModel(application)

    private val _state = MutableStateFlow<List<ServerUserData>>(emptyList())
    val state = _state.asStateFlow()

    fun fetchUsers() {
        viewModelScope.launch {
            _state.update {
                api.users.getUsersList().body()?.map {
                    it.copy(karma = karmaMock.store.getUserKarma(it._id!!.id!!))
                } ?: emptyList()
            }
        }
    }

}