package com.muslimlife.tf.footballappkotlang.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteMatch(
    val id: Long?,
    val matchId: String,
    val matchName: String,
    val matchDate: String,
    val homeTeamName: String,
    val homeTeamScore: String?,
    val awayTeamName: String,
    val awayTeamScore: String?
) : Parcelable