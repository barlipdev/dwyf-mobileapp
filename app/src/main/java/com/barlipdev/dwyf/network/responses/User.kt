package com.barlipdev.dwyf.network.responses

data class User(
    val avatarUrl: String,
    val email: String,
    val id: String,
    val password: String,
    val productList: List<Product>,
    val username: String
)