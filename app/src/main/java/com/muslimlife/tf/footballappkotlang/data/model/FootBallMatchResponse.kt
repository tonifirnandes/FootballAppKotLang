package com.muslimlife.tf.footballappkotlang.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Events(val events: List<Event>)

@Parcelize
data class Event(
    @SerializedName(EventFieldKey.eventName) val name: String,
    @SerializedName(EventFieldKey.eventDate) val date: String,
    @SerializedName(EventFieldKey.awayTeamId) val awayTeamId: String,
    @SerializedName(EventFieldKey.eventId) val id: String,
    @SerializedName(EventFieldKey.homeTeamId) val homeTeamId: String,
    @SerializedName(EventFieldKey.leagueId) val leagueId: String?,
    @SerializedName(EventFieldKey.awayScoreNumber) val awayScoreNumber: String?,
    @SerializedName(EventFieldKey.homeScoreNumber) val homeScoreNumber: String?,
    @SerializedName(EventFieldKey.awayGoalDetails) val awayGoalDetails: String?,
    @SerializedName(EventFieldKey.awayLineupDefense) val awayLineupDefense: String?,
    @SerializedName(EventFieldKey.awayLineupForward) val awayLineupForward: String?,
    @SerializedName(EventFieldKey.awayLineupGoalKeeper) val awayLineupGoalkeeper: String?,
    @SerializedName(EventFieldKey.awayLineupMidfield) val awayLineupMidfield: String?,
    @SerializedName(EventFieldKey.awayLineupSubtitutes) val awayLineupSubstitutes: String?,
    @SerializedName(EventFieldKey.awayTeamName) val awayTeamName: String,
    @SerializedName(EventFieldKey.homeGoalDetails) val homeGoalDetails: String?,
    @SerializedName(EventFieldKey.homeLineupDefense) val homeLineupDefense: String?,
    @SerializedName(EventFieldKey.homeLineupForward) val homeLineupForward: String?,
    @SerializedName(EventFieldKey.homeLineupGoalKeeper) val homeLineupGoalkeeper: String?,
    @SerializedName(EventFieldKey.homeLineupMidfield) val homeLineupMidfield: String?,
    @SerializedName(EventFieldKey.homeLineupSubtitutes) val homeLineupSubstitutes: String?,
    @SerializedName(EventFieldKey.homeTeamName) val homeTeamName: String,
    @SerializedName(EventFieldKey.homeTeamShotsNumber) val homeTeamShotsNumber: String?,
    @SerializedName(EventFieldKey.awayTeamShotsNumber) val awayTeamShotsNumber: String?
) : Parcelable