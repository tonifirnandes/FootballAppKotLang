package com.muslimlife.tf.footballappkotlang.data.model

import com.google.gson.annotations.SerializedName

data class Leagues(val leagues: List<League>, val selectedLeagueId: String, val selectedLeagueName: String)

data class League(
    @SerializedName(LeagueFieldKey.id)
    val id: String,
    @SerializedName(LeagueFieldKey.name)
    val name: String,
    @SerializedName(LeagueFieldKey.category)
    val category: String,
    @SerializedName(LeagueFieldKey.alternativeName)
    val alternativeName: String
)