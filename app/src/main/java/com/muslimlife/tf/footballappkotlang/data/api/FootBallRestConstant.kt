package com.muslimlife.tf.footballappkotlang.data.api

class FootBallRestConstant {
    companion object {
        const val baseUrl: String = "https://www.thesportsdb.com/api/v1/json/1/"
        const val allLeaguesEndPoint: String = "all_leagues.php"
        const val lastFifteenMatchesEndPoint: String = "eventspastleague.php"
        const val nextFifteenMatchesEndPoint: String = "eventsnextleague.php"
        const val teamDetailsEndPoint: String = "lookupteam.php"
        const val eventDetailsEndPoint: String = "lookupevent.php"
        const val searchEventEndPoint: String = "searchevents.php"
        const val searchTeamEndPoint: String = "searchteams.php"
        const val allTeamsEndPoint: String = "lookup_all_teams.php"
        const val allPlayersEndPoint: String = "lookup_all_players.php"
        const val playerDetailByIdEndPoint: String = "lookupplayer.php"
        const val queryParamsId: String = "id"
        const val apiSoccerCategory: String = "Soccer"
        const val querySearchEventParam: String = "e"
        const val querySearchTeamParam: String = "t"
        const val defaultSelectedLeagueId: String = "4328"
    }
}