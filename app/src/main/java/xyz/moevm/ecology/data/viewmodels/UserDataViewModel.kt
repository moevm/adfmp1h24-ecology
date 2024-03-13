package xyz.moevm.ecology.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.moevm.ecology.api.ApiViewModel
import xyz.moevm.ecology.api.types.ServerAuthData
import xyz.moevm.ecology.api.types.ServerUserData
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
                    userData?.copy(
                        karma = karmaMock.store.getUserKarma(userData._id!!.id!!)
                    )
                else userData
            }
        }
    }

    fun login(data: ServerAuthData): Job {
        return viewModelScope.launch { setUser(api.auth.login(data).body()) }
    }

    fun fetchUser(): Job {
        return viewModelScope.launch { setUser(api.auth.getLogin().body()) }
    }

    fun logout(): Job {
        return viewModelScope.launch {
            api.auth.logout()
            setUser(null)
        }
    }

    fun devLogin(): Job {
        return viewModelScope.launch { setUser(api.auth.devLogin().body()) }
    }
}