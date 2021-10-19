package com.barlipdev.dwyf.network.repository

import com.barlipdev.dwyf.network.AuthApi
import com.barlipdev.dwyf.network.responses.LoginData

class AuthRepository(private val api: AuthApi) : BaseRepository(){

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(LoginData(email,password))
    }

    suspend fun register(
        username: String,
        email: String,
        password: String
    ) = safeApiCall {
        api.register(username, email, password)
    }

}