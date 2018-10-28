package com.muslimlife.tf.footballappkotlang.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClubInfo(val name: String, val logoDrawableId: Int, val descriptions: String) : Parcelable