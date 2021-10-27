package com.barlipdev.dwyf.network.responses

data class Recipe(
    val id: String,
    val name: String,
    val productList: List<Product>,
    val description: String,
    val foodType: String
)
