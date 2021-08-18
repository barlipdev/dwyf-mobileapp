package com.barlipdev.dwyf.network.repository

import com.barlipdev.dwyf.network.AuthApi

class AuthRepository(private val api: AuthApi) : BaseRepository(){

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }

    suspend fun register(
        username: String,
        email: String,
        password: String
    ) = safeApiCall {
        api.register(username, email, password)
    }

}