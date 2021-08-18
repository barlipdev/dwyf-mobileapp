package com.barlipdev.dwyf.network.responses

import java.time.LocalDate
import java.util.*

data class Product(
    val id: String,
    var expirationDate: String,
    val name: String,
    val count: Double,
    val usefulnessState: UsefulnessState,
    val productType: ProductType,
    val splittedProductTags: List<String>,
    val productTag: String
)
