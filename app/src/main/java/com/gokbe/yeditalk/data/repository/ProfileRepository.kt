package com.gokbe.yeditalk.data.repository

import com.gokbe.yeditalk.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor() {
    // Initial mock data
    private val _currentUser = MutableStateFlow(
        User(
            name = "Gökberk (Kullanıcı Adı)",
            department = "Bilgisayar Mühendisliği | 4. Sınıf",
            bio = "Merhaba, ben bir YediTalk kullanıcısıyım.",
            avatarId = 0
        )
    )
    val currentUser: StateFlow<User> = _currentUser.asStateFlow()

    fun updateUser(name: String, department: String, bio: String) {
        val current = _currentUser.value
        _currentUser.value = current.copy(name = name, department = department, bio = bio)
    }

    fun updateAvatar(avatarId: Int) {
        val current = _currentUser.value
        _currentUser.value = current.copy(avatarId = avatarId)
    }
}
