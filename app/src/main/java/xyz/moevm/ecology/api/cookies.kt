package xyz.moevm.ecology.api

import kotlinx.coroutines.runBlocking
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import xyz.moevm.ecology.data.stores.CookiesViewModel

class DataStoreCookieJar(private val cookiesVM: CookiesViewModel) : CookieJar {
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        runBlocking {
            cookiesVM.store.setCookies(cookies)
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return runBlocking {
            return@runBlocking cookiesVM.store
                .getCookies()
                ?.map { Cookie.parse(url, it) }
                ?.filterNotNull()
        } ?: emptyList()
    }
}