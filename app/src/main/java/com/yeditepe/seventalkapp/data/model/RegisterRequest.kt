package com.yeditepe.seventalkapp.data.model

data class RegisterRequest(
    val fullName: String,
    val faculty: String,
    val department: String,
    val grade: String
)