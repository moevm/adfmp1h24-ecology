package xyz.moevm.ecology.data.types

data class AuthData(var login: String, var password: String)

data class UserProfileData(
    var login: String,
    var password: String,
    var name: String,
    val karma: Int,
    var role: String
)
