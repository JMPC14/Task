package com.jack.utilitatask.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UtilitaStatus(
    val url: String?,
    val responseCode: Int,
    val responseTime: Double?,
    val `class`: String
): Parcelable