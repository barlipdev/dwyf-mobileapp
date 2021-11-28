package com.barlipdev.dwyf.network.repository

import com.barlipdev.dwyf.network.PerformingRecipeApi
import com.barlipdev.dwyf.network.responses.PerformingRecipe

class PerformingRecipeRepository(private val api: PerformingRecipeApi) : BaseRepository() {

    suspend fun getPerformingRecipes(
        userId: String
    ) = safeApiCall {
        api.getPerformingRecipes(userId)
    }

    suspend fun performRecipe(
        userId: String,
        performingRecipe: PerformingRecipe
    ) = safeApiCall {
        api.performRecipe(userId, performingRecipe)
    }

    suspend fun updatePerformRecipe(
        performingRecipe: PerformingRecipe
    ) = safeApiCall {
        api.updatePerformRecipe(performingRecipe)
    }

}