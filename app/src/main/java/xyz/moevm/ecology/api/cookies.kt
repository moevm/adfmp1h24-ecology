package xyz.moevm.ecology.api

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class SessionCookieJar : CookieJar {
    var cookies: List<Cookie> = emptyList()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        this.cookies = ArrayList(cookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookies
    }
}