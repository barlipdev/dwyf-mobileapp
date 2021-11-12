package com.barlipdev.dwyf.network.repository

import com.barlipdev.dwyf.network.RecipeApi
import com.barlipdev.dwyf.network.responses.FoodTypeFilter
import com.barlipdev.dwyf.network.responses.ProductFilter

class RecipeRepository(private val api: RecipeApi) : BaseRepository() {

    suspend fun getPrefferedRecipe(
        userId: String,
        productFilter: ProductFilter,
        foodTypeFilter: FoodTypeFilter
    ) = safeApiCall {
        api.getPrefferedRecipe(userId,productFilter, foodTypeFilter)
    }

}