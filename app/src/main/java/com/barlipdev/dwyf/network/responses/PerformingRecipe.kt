package com.barlipdev.dwyf.network.responses

import java.time.LocalDate

data class PerformingRecipe(
    val id: String,
    val userId: String,
    val matchedRecipe: MatchedRecipe,
    val createdOn: String,
    var recipeStatus: RecipeStatus
)
