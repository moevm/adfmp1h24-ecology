package xyz.moevm.ecology.data.stores

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okhttp3.Cookie

class CookiesStore(private val context: Context) {
    companion object {
        private val Context.cookiesStore: DataStore<Preferences> by preferencesDataStore(name = "cookies")
        val COOKIES_KEY = stringSetPreferencesKey("cookies")
    }

    suspend fun setCookies(cookies: List<Cookie>) {
        context.cookiesStore.edit { prefs ->
            prefs[COOKIES_KEY] = cookies.map { it.toString() }.toSet()
        }
    }

    suspend fun getCookies(): Set<String>? {
        return context.cookiesStore.data.map { it[COOKIES_KEY] }.first()
    }
}

class CookiesViewModel(application: Application) : AndroidViewModel(application) {
    val store = CookiesStore(application)
}