package com.barlipdev.dwyf.network

import com.barlipdev.dwyf.network.responses.LoginData
import com.barlipdev.dwyf.network.responses.LoginResponse
import com.barlipdev.dwyf.network.responses.User
import retrofit2.http.*

interface AuthApi {

    @POST("login")
    suspend fun login(
        @Body loginData: LoginData
    ) : LoginResponse

    @POST("register")
    suspend fun register(
        @Body user: User
    ) : User
}