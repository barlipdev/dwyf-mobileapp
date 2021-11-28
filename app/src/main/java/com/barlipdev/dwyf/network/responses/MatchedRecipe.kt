package com.barlipdev.dwyf.network.responses

data class MatchedRecipe(
    val recipe: Recipe,
    val availableProducts: List<Product>,
    val notAvailableProducts: List<Product>,
    val removedProducts: List<RemovedProduct>
)
