package com.barlipdev.dwyf.network.repository

import android.util.Log
import com.barlipdev.dwyf.network.UserApi
import com.barlipdev.dwyf.network.responses.Product

class UserRepository(private val api: UserApi) :BaseRepository(){

    suspend fun addProductByBarcode(
        userId: String,
        product: Product) = safeApiCall {
            api.addProductByBarcode(userId,product)

    }

    suspend fun addProduct(
        userId: String,
        product: Product
    ) = safeApiCall {
        api.addProduct(userId,product)
    }

}