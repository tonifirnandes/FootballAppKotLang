package com.muslimlife.tf.footballappkotlang.data.api

class FootBallRestConstant {
    companion object {
        const val baseUrl = "https://www.thesportsdb.com/api/v1/json/1/"
        const val allLeaguesEndPoint = "all_leagues.php"
        const val lastFifteenMatchesEndPoint = "eventspastleague.php"
        const val nextFifteenMatchesEndPoint = "eventsnextleague.php"
        const val teamDetailsEndPoint = "lookupteam.php"
        const val queryParamsId = "id"
        const val apiSoccerCategory = "Soccer"
    }
}