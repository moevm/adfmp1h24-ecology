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
import xyz.moevm.ecology.api.types.ServerUserEditData
import xyz.moevm.ecology.data.stores.KarmaMockViewModel

class UsersViewModel(application: Application) : AndroidViewModel(application) {
    private val api = ApiViewModel(application)
    private val karmaMock = KarmaMockViewModel(application)

    private val usersListState = MutableStateFlow<List<ServerUserData>>(emptyList())
    val usersList = usersListState.asStateFlow()

    private val currentUserState = MutableStateFlow<ServerUserData?>(null)
    val currentUser = currentUserState.asStateFlow()

    private suspend fun fetchUser(id: String) = api.users.getUser(id).body()?.copy(
        karma = karmaMock.store.getUserKarma(id)
    )

    private suspend fun updateUserKarma(selfId: String, user: ServerUserData, value: Int): ServerUserData {
        val karma = karmaMock.store.getUserKarma(user._id!!.id!!)
        val vote = karmaMock.store.getVote(selfId, user._id.id!!)

        val newVal =
            if ((vote == false && value < 0) || (vote == true && value > 0)) 0
            else if (
                (vote == false && value > 0) ||
                (vote == true && value < 0)
            ) 2 * value
            else value

        karmaMock.store.setUserKarma(user._id.id, karma + newVal)

        if (newVal != 0)
            karmaMock.store.vote(selfId, user._id.id, value > 0)

        return user.copy(karma = karma + newVal)
    }

    private suspend fun edit(id: String, data: ServerUserEditData) {
        api.users.editUser(id, data)
    }

    fun fetchUsers()= viewModelScope.launch {
        var respUsersListState: List<ServerUserData> = emptyList()

        kotlin.runCatching {
            respUsersListState = api.users.getUsersList().body()?.map {
                it.copy(karma = karmaMock.store.getUserKarma(it._id!!.id!!))
            } ?: emptyList()
        }.onSuccess {
            usersListState.update { respUsersListState }
        }.onFailure{
            // Можно что-то сделать с ошибкой, но логи тоже ничего )
            it.printStackTrace()
        }
    }

    fun fetchCurrentUser(id: String) = viewModelScope.launch {
        var respUserState: ServerUserData? = null

        kotlin.runCatching {
            respUserState = fetchUser(id)
        }.onSuccess {
            currentUserState.update { respUserState }
        }.onFailure{
            // Можно что-то сделать с ошибкой, но логи тоже ничего )
            it.printStackTrace()
        }
    }

    fun updateCurrentUserKarma(selfId: String, value: Int)= viewModelScope.launch {
        var respUserState: ServerUserData? = null

        kotlin.runCatching {
            respUserState = updateUserKarma(selfId, currentUserState.value!!, value)
        }.onSuccess {
            currentUserState.update { respUserState }
        }.onFailure{
            // Можно что-то сделать с ошибкой, но логи тоже ничего )
            it.printStackTrace()
        }
    }

    fun editCurrentUser(data: ServerUserEditData) = viewModelScope.launch {
        kotlin.runCatching {
            edit(currentUser.value!!._id!!.id!!, data)
        }.onSuccess {
            currentUserState.update {
                it?.copy(login = data.login, password = data.password, name = data.name)
            }
        }.onFailure{
            // Можно что-то сделать с ошибкой, но логи тоже ничего )
            it.printStackTrace()
        }
    }
}