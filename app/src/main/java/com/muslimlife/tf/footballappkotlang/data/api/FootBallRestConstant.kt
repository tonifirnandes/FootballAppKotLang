package com.muslimlife.tf.footballappkotlang.data.api

class FootBallRestConstant {
    companion object {
        const val baseUrl: String = "https://www.thesportsdb.com/api/v1/json/1/"
        const val allLeaguesEndPoint: String = "all_leagues.php"
        const val lastFifteenMatchesEndPoint: String = "eventspastleague.php"
        const val nextFifteenMatchesEndPoint: String = "eventsnextleague.php"
        const val teamDetailsEndPoint: String = "lookupteam.php"
        const val eventDetailsEndPoint: String = "lookupevent.php"
        const val queryParamsId: String = "id"
        const val apiSoccerCategory: String = "Soccer"
    }
}