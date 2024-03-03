package com.example.adfmpecology.data.types

data class AuthData(var login: String, var password: String)

data class UserProfileData(var login: String, var password: String, var name: String)

data class UserData(
    var login: String,
    var password: String,
    var name: String,
    var role: String,
    var karma: Int
)
