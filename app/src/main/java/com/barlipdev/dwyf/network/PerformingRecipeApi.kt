package com.barlipdev.dwyf.network

import com.barlipdev.dwyf.network.responses.PerformingRecipe
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PerformingRecipeApi {

    @GET("performing/all")
    suspend fun getPerformingRecipes(
        @Query("userId") userId: String
    ) : List<PerformingRecipe>

    @POST("performing/perform")
    suspend fun performRecipe(
        @Query("userId") userId: String,
        @Body performingRecipe: PerformingRecipe
    ) : PerformingRecipe

    @POST("performing/update")
    suspend fun updatePerformRecipe(
        @Body performingRecipe: PerformingRecipe
    ) : PerformingRecipe

}