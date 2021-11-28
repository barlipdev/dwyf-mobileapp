package com.barlipdev.dwyf.network.repository

import android.util.Log
import com.barlipdev.dwyf.network.UserApi
import com.barlipdev.dwyf.network.responses.Product
import com.barlipdev.dwyf.network.responses.ShoppingList

class UserRepository(private val api: UserApi) :BaseRepository(){

    suspend fun addProductByBarcode(
        userId: String,
        product: Product) = safeApiCall {
            api.addProductByBarcode(userId,product)

    }

    suspend fun createShoppingList(
        userId: String,
        shoppingList: ShoppingList
    ) = safeApiCall{
        api.createShoppingList(userId,shoppingList)
    }

    suspend fun addProduct(
        userId: String,
        product: Product
    ) = safeApiCall {
        api.addProduct(userId,product)
    }

    suspend fun getProductList(
        userId: String
    ) = safeApiCall {
        api.getProductList(userId)
    }

    suspend fun getShoppingLists(
        userId: String
    ) = safeApiCall {
        api.getShoppingLists(userId)
    }

    suspend fun deleteExpiredProducts(
        userId: String
    ) = safeApiCall {
        api.deleteExpiredProducts(userId)
    }

    suspend fun deleteShoppingList(
        userId: String,
        shoppingList: ShoppingList
    ) = safeApiCall {
        api.deleteShoppingList(userId, shoppingList)
    }

}