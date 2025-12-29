package com.gokbe.yeditalk.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gokbe.yeditalk.data.model.User
import com.gokbe.yeditalk.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    val user: StateFlow<User> = repository.currentUser
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = User("Yükleniyor...", "", "")
        )

    fun updateProfile(name: String, department: String, bio: String) {
        repository.updateUser(name, department, bio)
    }

    fun updateAvatar(avatarId: Int) {
        repository.updateAvatar(avatarId)
    }
}
