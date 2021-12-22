package com.barlipdev.dwyf.network.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchedRecipe(
    val recipe: Recipe,
    val availableProducts: List<Product>,
    val notAvailableProducts: List<Product>,
    val removedProducts: List<RemovedProduct>
) : Parcelable
