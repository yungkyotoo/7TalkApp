package com.yeditepe.seventalkapp.data.api

import com.yeditepe.seventalkapp.data.model.Club
import com.yeditepe.seventalkapp.data.model.Faculty
import com.yeditepe.seventalkapp.data.model.LoginRequest
import com.yeditepe.seventalkapp.data.model.LoginResponse
import com.yeditepe.seventalkapp.data.model.RegisterRequest
import com.yeditepe.seventalkapp.data.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("clubs")
    suspend fun getClubs(): List<Club>

    @GET("faculties")
    suspend fun getFaculties(): List<Faculty>

    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): RegisterResponse

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): LoginResponse
}