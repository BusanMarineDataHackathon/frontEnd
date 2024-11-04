package com.example.net_flicks.api

import com.example.net_flicks.api.model.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/v1/auth/login")
    fun login(@Body loginRequest: LoginRequest ) : Call<Void>
}