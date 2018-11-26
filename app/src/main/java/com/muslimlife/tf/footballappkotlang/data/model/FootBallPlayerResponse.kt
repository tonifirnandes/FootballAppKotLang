package com.muslimlife.tf.footballappkotlang.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class FootBallPlayerList(
    var player: List<Player>
)

data class FootBallPlayerDetail(
    var players: List<Player>
)

@Parcelize
data class Player(
    @SerializedName(FootBallPlayerFieldKey.id)
    var id: String,
    @SerializedName(FootBallPlayerFieldKey.faceLogoUrl)
    var faceLogoUrl: String?,
    @SerializedName(FootBallPlayerFieldKey.descriptionInEnglish)
    var descriptionInEnglish: String?,
    @SerializedName(FootBallPlayerFieldKey.fanArt1)
    var fanArt1Url: String?,
    @SerializedName(FootBallPlayerFieldKey.height)
    var height: String?,
    @SerializedName(FootBallPlayerFieldKey.name)
    var name: String?,
    @SerializedName(FootBallPlayerFieldKey.position)
    var position: String?,
    @SerializedName(FootBallPlayerFieldKey.weight)
    var weight: String?
) : Parcelable