package com.muslimlife.tf.footballappkotlang.data.preference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestConstant
import com.muslimlife.tf.footballappkotlang.data.model.Leagues

object SharedPrefs {
    private lateinit var preferences: SharedPreferences
    private lateinit var gson: Gson
    private const val fileName = "com.muslimlife.tf.footballappkotlang.prefs"

    object Key {
        const val leagues: String = "leagues"
        const val selectedLeagueId: String = "selected_league_id"
        const val selectedLeagueName: String = "selected_league_name"
    }

    fun init(context: Context, gsonLib: Gson) {
        preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        gson = gsonLib
    }

    var selectedLeagueId: String?
        get() = preferences.getString(
            Key.selectedLeagueId,
            FootBallRestConstant.defaultSelectedLeagueId
        ) //default league
        set(value) = preferences.edit().putString(Key.selectedLeagueId, value).apply()

    var selectedLeagueName: String?
        get() = preferences.getString(Key.selectedLeagueName, "")
        set(value) = preferences.edit().putString(Key.selectedLeagueName, value).apply()

    var leaguesData: Leagues?
        get() = gson.fromJson(preferences.getString(Key.leagues, null), Leagues::class.java)
        set(value) = preferences.edit().putString(Key.leagues, gson.toJson(value)).apply()
}
