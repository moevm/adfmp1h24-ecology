package xyz.moevm.ecology.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.moevm.ecology.api.ApiViewModel
import xyz.moevm.ecology.api.types.ServerAuthData
import xyz.moevm.ecology.api.types.ServerUserData

class UserDataViewModel(application: Application) : AndroidViewModel(application) {
    private val api = ApiViewModel(application)

    private val _state = MutableStateFlow<ServerUserData?>(null)
    val state: StateFlow<ServerUserData?> = _state.asStateFlow()

    private fun setUser(userData: ServerUserData?) {
        _state.update { userData }
    }

    fun login(data: ServerAuthData) {
        viewModelScope.launch { setUser(api.auth.login(data).body()) }
    }

    fun fetchUser() {
        viewModelScope.launch { setUser(api.auth.getLogin().body()) }
    }

    fun logout() {
        viewModelScope.launch {
            api.auth.logout()
            setUser(null)
        }
    }

    fun devLogin() {
        viewModelScope.launch { setUser(api.auth.devLogin().body()) }
    }
}