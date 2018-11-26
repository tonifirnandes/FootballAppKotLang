package com.muslimlife.tf.footballappkotlang.data.model.local_db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteTeam(
    val id: Long?,
    val teamId: String,
    val teamName: String,
    val teamLogoUrl: String
) : Parcelable