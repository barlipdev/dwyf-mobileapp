package com.barlipdev.dwyf.network.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    val id: String,
    val name: String,
    val productList: List<Product>,
    val description: String,
    val foodType: String
) : Parcelable
