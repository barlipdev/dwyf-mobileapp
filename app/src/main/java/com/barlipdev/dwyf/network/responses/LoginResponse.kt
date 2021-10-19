package com.barlipdev.dwyf.network.responses

data class LoginResponse(
    val authToken: String,
    val user: User
)
