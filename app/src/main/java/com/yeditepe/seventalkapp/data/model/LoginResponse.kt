package com.yeditepe.seventalkapp.data.model

data class LoginResponse(
    val token: String,
    val userName: String,
    val status: String
)