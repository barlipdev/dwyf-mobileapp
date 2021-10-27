package com.barlipdev.dwyf.network

import com.barlipdev.dwyf.network.responses.MatchedRecipe
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("recipe/best")
    suspend fun getPrefferedRecipe(
        @Query("userId") userId: String
    ) : MatchedRecipe

}