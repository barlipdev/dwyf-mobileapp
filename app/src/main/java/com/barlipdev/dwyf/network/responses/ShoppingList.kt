package com.barlipdev.dwyf.network.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShoppingList(
    var id: String,
    var name: String,
    var productList: List<Product>
): Parcelable
