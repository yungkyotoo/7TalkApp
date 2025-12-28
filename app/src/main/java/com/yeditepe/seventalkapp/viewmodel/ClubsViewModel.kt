package com.yeditepe.seventalkapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeditepe.seventalkapp.data.model.Club
import com.yeditepe.seventalkapp.data.model.Faculty
// RetrofitClient dosyanın doğru import edildiğinden emin ol (Android Studio Alt+Enter ile bulur)
import com.yeditepe.seventalkapp.data.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClubsViewModel : ViewModel() {

    private val _clubs = MutableStateFlow<List<Club>>(emptyList())
    val clubs: StateFlow<List<Club>> = _clubs

    private val _faculties = MutableStateFlow<List<Faculty>>(emptyList())
    val faculties: StateFlow<List<Faculty>> = _faculties

    init {
        fetchClubs()
        fetchFaculties()
    }

    private fun fetchClubs() {
        viewModelScope.launch {
            try {
                // ARTIK HATA VERMEZ: Çünkü ApiService artık direkt List döndürüyor
                val response = RetrofitClient.apiService.getClubs()
                _clubs.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchFaculties() {
        viewModelScope.launch {
            try {
                // ARTIK HATA VERMEZ
                val response = RetrofitClient.apiService.getFaculties()
                _faculties.value = response
            } catch (e: Exception) {
                println("Fakülte Hatası: ${e.message}")
            }
        }
    }
}