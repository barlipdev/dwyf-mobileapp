package com.barlipdev.dwyf.network

import com.barlipdev.dwyf.network.responses.Product
import com.barlipdev.dwyf.network.responses.User
import retrofit2.http.*

interface UserApi {

    @POST("users/product/barcode")
    suspend fun addProductByBarcode(
        @Query("userId") userId: String,
        @Body product: Product
    ) : Product

    @POST("/users/product")
    suspend fun addProduct(
        @Query("userId") userId: String,
        @Body product: Product
    ) : Product

    @GET("/users/product")
    suspend fun getProductList(
        @Query("userId") userId: String
    ) : List<Product>
}