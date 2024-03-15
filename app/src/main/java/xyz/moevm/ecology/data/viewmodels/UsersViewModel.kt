package xyz.moevm.ecology.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
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

    private val usersListState = MutableStateFlow<List<ServerUserData>>(emptyList())
    val usersList = usersListState.asStateFlow()

    fun fetchUsers(): Job {
        return viewModelScope.launch {
            usersListState.update {
                api.users.getUsersList().body()?.map {
                    it.copy(karma = karmaMock.store.getUserKarma(it._id!!.id!!))
                } ?: emptyList()
            }
        }
    }

    private val currentUserState = MutableStateFlow<ServerUserData?>(null)
    val currentUser = currentUserState.asStateFlow()

    suspend fun fetchUser(id: String) = api.users.getUser(id).body()?.copy(
        karma = karmaMock.store.getUserKarma(id)
    )

    fun fetchCurrentUser(id: String): Job {
        return viewModelScope.launch {
            currentUserState.update {
                fetchUser(id)
            }
        }
    }

    suspend fun updateUserKarma(user: ServerUserData, value: Int): ServerUserData {
        val karma = karmaMock.store.getUserKarma(user._id!!.id!!)
        karmaMock.store.setUserKarma(
            user._id.id!!, karma + value
        )

        return user.copy(karma = karma + value)
    }

    fun updateCurrentUserKarma(value: Int): Job {
        return viewModelScope.launch {
            currentUserState.update {
                updateUserKarma(currentUserState.value!!, value)
            }
        }
    }

}