package xyz.moevm.ecology.api.types

import com.google.gson.annotations.SerializedName

data class _Id(
    @SerializedName("\$oid")
    val id: String? = null
)

data class ServerUserData(
    val _id: _Id? = null,
    val login: String? = null,
    val password: String? = null,
    val name: String? = null,
    val role: String? = null
)

data class ServerAuthData(
    val login: String,
    val password: String,
)