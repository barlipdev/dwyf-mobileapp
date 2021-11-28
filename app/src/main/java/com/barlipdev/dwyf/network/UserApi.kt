package com.barlipdev.dwyf.network

import com.barlipdev.dwyf.network.responses.Product
import com.barlipdev.dwyf.network.responses.ShoppingList
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

    @POST("/users/shoppingList")
    suspend fun createShoppingList(
        @Query("userId") userId: String,
        @Body shoppingList: ShoppingList
    ) : User

    @GET("/users/product")
    suspend fun getProductList(
        @Query("userId") userId: String
    ) : List<Product>

    @GET("/users/shoppingList")
    suspend fun getShoppingLists(
        @Query("userId") userId: String
    ) : List<ShoppingList>

    @DELETE("/users/product/all")
    suspend fun deleteExpiredProducts(
        @Query("userId") userId: String
    ) : User

    @POST("/users/shoppingList/delete")
    suspend fun deleteShoppingList(
        @Query("userId") userId: String,
        @Body shoppingList: ShoppingList
    ) : User
}