package com.gokbe.yeditalk.data.model

data class User(
    val name: String,
    val department: String,
    val bio: String,
    val avatarId: Int = 0
)
