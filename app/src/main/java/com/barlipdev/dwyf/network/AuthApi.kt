package com.barlipdev.dwyf.network

import com.barlipdev.dwyf.network.responses.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("login/v2")
    suspend fun login(
        @Field("email") email: String ,
        @Field("password") password: String
    ) : User

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : User

}