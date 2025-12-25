package com.yeditepe.seventalkapp.data.api

import com.yeditepe.seventalkapp.data.model.Club
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("clubs")
    fun getClubs(): Call<List<Club>>
}