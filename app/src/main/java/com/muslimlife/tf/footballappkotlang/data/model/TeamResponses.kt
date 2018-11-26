package com.muslimlife.tf.footballappkotlang.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Teams(val teams: List<Team>)

@Parcelize
data class Team(
    @SerializedName(TeamFieldKey.leagueId)
    val leagueId: String,
    @SerializedName(TeamFieldKey.badgeUrl)
    val badgeUrl: String,
    @SerializedName(TeamFieldKey.id)
    var id: String,
    @SerializedName(TeamFieldKey.descriptionInEnglish)
    var descriptionInEnglish: String?,
    @SerializedName(TeamFieldKey.stadiumName)
    var stadiumName: String?,
    @SerializedName(TeamFieldKey.name)
    var name: String,
    @SerializedName(TeamFieldKey.establishedYear)
    var establishedYear: String
) : Parcelable