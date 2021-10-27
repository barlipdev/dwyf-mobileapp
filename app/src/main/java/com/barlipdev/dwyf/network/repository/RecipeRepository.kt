package com.barlipdev.dwyf.network.repository

import com.barlipdev.dwyf.network.RecipeApi

class RecipeRepository(private val api: RecipeApi) : BaseRepository() {

    suspend fun getPrefferedRecipe(
        userId: String
    ) = safeApiCall {
        api.getPrefferedRecipe(userId)
    }

}