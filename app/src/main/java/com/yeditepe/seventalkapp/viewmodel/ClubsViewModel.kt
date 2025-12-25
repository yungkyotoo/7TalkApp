package com.yeditepe.seventalkapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeditepe.seventalkapp.data.api.RetrofitClient
import com.yeditepe.seventalkapp.data.model.Club
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClubsViewModel : ViewModel() {

    // Ekrana haber veren canlı veri kutusu
    private val _clubs = MutableStateFlow<List<Club>>(emptyList())
    val clubs: StateFlow<List<Club>> = _clubs.asStateFlow()

    init {
        // ViewModel ilk oluştuğunda direkt verileri çek
        viewModelScope.launch {
            loadClubs()
        }
    }

    private fun loadClubs() {
        // Artık elle yazılan liste YOK. Direkt Retrofit ile sunucuya gidiyoruz.
        RetrofitClient.apiService.getClubs().enqueue(object : Callback<List<Club>> {
            override fun onResponse(call: Call<List<Club>>, response: Response<List<Club>>) {
                if (response.isSuccessful) {
                    // Veri başarıyla geldiyse kutuya koy
                    response.body()?.let { incomingList ->
                        _clubs.value = incomingList
                    }
                }
            }

            override fun onFailure(call: Call<List<Club>>, t: Throwable) {
                println("API Hatası: ${t.message}")
            }
        })
    }
}