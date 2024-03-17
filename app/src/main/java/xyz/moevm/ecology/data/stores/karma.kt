package xyz.moevm.ecology.data.stores

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class KarmaMockStore(private val context: Context) {
    companion object {
        private val Context.karmaMockStore: DataStore<Preferences> by preferencesDataStore(name = "karma-mock")
    }

    suspend fun vote(id1: String, id2: String, value: Boolean) {
        val key = booleanPreferencesKey("$id1-$id2")
        context.karmaMockStore.edit { prefs ->
            prefs[key] = value
        }
    }

    suspend fun getVote(id1: String, id2: String): Boolean? {
        val key = booleanPreferencesKey("$id1-$id2")
        return context.karmaMockStore.data.map { it[key] }.first()
    }

    suspend fun setUserKarma(id: String, karma: Int) {
        val key = intPreferencesKey(id)
        context.karmaMockStore.edit { prefs ->
            prefs[key] = karma
        }
    }

    suspend fun getUserKarma(id: String): Int {
        val key = intPreferencesKey(id)
        val karma = context.karmaMockStore.data.map { it[key] }.first()
        return if (karma === null) {
            setUserKarma(id, 0)
            0
        } else karma
    }
}

class KarmaMockViewModel(application: Application) : AndroidViewModel(application) {
    val store = KarmaMockStore(application)
}