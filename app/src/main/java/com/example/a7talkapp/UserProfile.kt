package com.example.a7talkapp

data class UserProfile(
    val name: String,
    val department: String,
    val bio: String,
    val followers: String,
    val following: String,
    val interests: List<String>
)
