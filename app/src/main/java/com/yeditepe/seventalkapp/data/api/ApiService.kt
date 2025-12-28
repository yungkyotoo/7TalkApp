package com.yeditepe.seventalkapp.data.api

import com.yeditepe.seventalkapp.data.model.Club
import com.yeditepe.seventalkapp.data.model.Faculty
import retrofit2.http.GET

interface ApiService {

    // Call<...> YOK! Yerine 'suspend' var.
    @GET("clubs")
    suspend fun getClubs(): List<Club>

    @GET("faculties")
    suspend fun getFaculties(): List<Faculty>
}