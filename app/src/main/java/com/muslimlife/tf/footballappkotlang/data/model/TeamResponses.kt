package com.muslimlife.tf.footballappkotlang.data.model

import com.google.gson.annotations.SerializedName

data class Teams(val teams: List<Team>)

data class Team(
    @SerializedName(TeamFieldKey.leagueId)
    val leagueId: String,
    @SerializedName(TeamFieldKey.badgeUrl)
    val badgeUrl: String
)