package com.barlipdev.dwyf.network.repository

import com.barlipdev.dwyf.network.AuthApi
import com.barlipdev.dwyf.network.responses.LoginData
import com.barlipdev.dwyf.network.responses.User

class AuthRepository(private val api: AuthApi) : BaseRepository(){

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(LoginData(email,password))
    }

    suspend fun register(
        user: User
    ) = safeApiCall {
        api.register(user)
    }

}