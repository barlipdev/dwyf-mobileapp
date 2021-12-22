package com.barlipdev.dwyf.network

import com.barlipdev.dwyf.network.responses.FoodTypeFilter
import com.barlipdev.dwyf.network.responses.MatchedRecipe
import com.barlipdev.dwyf.network.responses.PerformingRecipe
import com.barlipdev.dwyf.network.responses.ProductFilter
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RecipeApi {

    @GET("recipe/best")
    suspend fun getPrefferedRecipe(
        @Query("userId") userId: String,
        @Query("productFilter") productFilter: ProductFilter,
        @Query("foodTypeFilter") foodTypeFilter: FoodTypeFilter
    ) : List<MatchedRecipe>

    @POST("recipe/best/addPerform")
    suspend fun addPerform(
        @Query("userId") userId: String,
        @Body matchedRecipe: MatchedRecipe
    ) : PerformingRecipe

}