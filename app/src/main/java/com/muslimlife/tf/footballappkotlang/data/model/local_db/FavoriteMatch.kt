package com.muslimlife.tf.footballappkotlang.data.model.local_db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteMatch(
    val id: Long?,
    val matchId: String,
    val matchName: String,
    val matchDate: String?,
    val matchTime: String?,
    val homeTeamName: String,
    val homeTeamScore: String?,
    val awayTeamName: String,
    val awayTeamScore: String?
) : Parcelable