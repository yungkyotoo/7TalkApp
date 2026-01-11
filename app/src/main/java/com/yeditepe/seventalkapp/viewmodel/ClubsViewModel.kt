package com.yeditepe.seventalkapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeditepe.seventalkapp.data.api.RetrofitClient
import com.yeditepe.seventalkapp.data.model.Club
import com.yeditepe.seventalkapp.data.model.Faculty
import com.yeditepe.seventalkapp.data.model.LoginRequest
import com.yeditepe.seventalkapp.data.model.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClubsViewModel : ViewModel() {

    // KULÜPLER STATE
    private val _clubs = MutableStateFlow<List<Club>>(emptyList())
    val clubs: StateFlow<List<Club>> = _clubs

    // FAKÜLTELER STATE
    private val _faculties = MutableStateFlow<List<Faculty>>(emptyList())
    val faculties: StateFlow<List<Faculty>> = _faculties

    init {
        fetchClubs()
        fetchFaculties()
    }

    private fun fetchClubs() {
        viewModelScope.launch {
            try {
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
                val response = RetrofitClient.apiService.getFaculties()
                _faculties.value = response
            } catch (e: Exception) {
                println("Fakülte Hatası: ${e.message}")
            }
        }
    }

    // --- KAYIT OLMA FONKSİYONU ---
    fun registerUser(
        fullName: String,
        faculty: String,
        department: String,
        grade: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                // 1. Verileri paketle
                val request = RegisterRequest(fullName, faculty, department, grade)

                // 2. Sunucuya gönder
                val response = RetrofitClient.apiService.registerUser(request)

                // 3. Logcat kontrolü
                Log.d("Register", "Sunucu Cevabı: ${response.message}")

                // 4. Başarılıysa işlemi tamamla
                if (response.status == "success") {
                    onSuccess()
                }

            } catch (e: Exception) {
                Log.e("Register", "Kayıt Hatası: ${e.message}")
            }
        }
    }

    fun loginUser(email: String, password: String, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(email, password)
                val response = RetrofitClient.apiService.loginUser(request)

                Log.d("Login", "Giriş Başarılı: ${response.userName}")

                if (response.status == "success") {
                    onSuccess(response.userName)
                }
            } catch (e: Exception) {
                Log.e("Login", "Giriş Hatası: ${e.message}")
            }
        }
    }
}