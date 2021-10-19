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

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : User
}