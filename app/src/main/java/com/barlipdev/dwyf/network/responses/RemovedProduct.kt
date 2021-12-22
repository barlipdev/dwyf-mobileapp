package com.barlipdev.dwyf.network.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RemovedProduct(
    val name: String,
    val count: Double
) : Parcelable
