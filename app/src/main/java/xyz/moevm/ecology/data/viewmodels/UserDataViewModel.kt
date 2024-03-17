package xyz.moevm.ecology.data.viewmodels

import android.app.Application
import android.service.autofill.UserData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.moevm.ecology.api.ApiViewModel
import xyz.moevm.ecology.api.types.ObjectInfo
import xyz.moevm.ecology.api.types.ServerAuthData
import xyz.moevm.ecology.api.types.ServerUserData
import xyz.moevm.ecology.api.types.ServerUserEditData
import xyz.moevm.ecology.data.stores.KarmaMockViewModel

class UserDataViewModel(application: Application) : AndroidViewModel(application) {
    private val api = ApiViewModel(application)
    private val karmaMock = KarmaMockViewModel(application)

    private val _state = MutableStateFlow<ServerUserData?>(null)
    val state: StateFlow<ServerUserData?> = _state.asStateFlow()

    private fun setUser(userData: ServerUserData?) {
        viewModelScope.launch {
            _state.update {
                if (userData?.karma === null)
                    userData?.copy(karma = karmaMock.store.getUserKarma(userData._id!!.id!!))
                else userData
            }
        }
    }

    fun login(data: ServerAuthData) = viewModelScope.launch {
        var respUser: ServerUserData? = null

        kotlin.runCatching {
            respUser = api.auth.login(data).body()
        }.onSuccess {
            setUser(respUser)
        }.onFailure{
            // Можно что-то сделать с ошибкой, но логи тоже ничего )
            it.printStackTrace()
        }
    }

    fun fetchUser() = viewModelScope.launch {
        var respUser: ServerUserData? = null

        kotlin.runCatching {
            respUser = api.auth.getLogin().body()
        }.onSuccess {
            setUser(respUser)
        }.onFailure{
            // Можно что-то сделать с ошибкой, но логи тоже ничего )
            it.printStackTrace()
        }
    }

    fun logout() = viewModelScope.launch {
        kotlin.runCatching {
            api.auth.logout()
        }.onSuccess {
            setUser(null)
        }.onFailure{
            // Можно что-то сделать с ошибкой, но логи тоже ничего )
            it.printStackTrace()
        }
    }

    fun devLogin() = viewModelScope.launch {
        var respUser: ServerUserData? = null

        kotlin.runCatching {
            respUser = api.auth.devLogin().body()
        }.onSuccess {
            setUser(respUser)
        }.onFailure{
            // Можно что-то сделать с ошибкой, но логи тоже ничего )
            it.printStackTrace()
        }
    }

    fun edit(data: ServerUserEditData) = viewModelScope.launch {
        kotlin.runCatching {
            api.users.editSelf(data)
        }.onSuccess {
            _state.update { it?.copy(login = data.login, password = data.password, name = data.name) }
        }.onFailure{
            // Можно что-то сделать с ошибкой, но логи тоже ничего )
            it.printStackTrace()
        }
    }
}