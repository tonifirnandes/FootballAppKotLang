package com.muslimlife.tf.footballappkotlang.data.db

class DbConstant {
    companion object {
        const val name: String = "footballappkotlang.db"
        const val version: Int = 1

        object FavoriteMatchTable {
            const val name: String = "tbl_favorite_match"

            object Column {
                const val id: String = "id"
                const val matchId: String = "matchId"
                const val matchName: String = "matchName"
                const val matchDate: String = "matchDate"
                const val matchTime: String = "matchTime"
                const val homeTeamName: String = "homeTeamName"
                const val homeTeamScore: String = "homeTeamScore"
                const val awayTeamName: String = "awayTeamName"
                const val awayTeamScore: String = "awayTeamScore"
            }
        }

        object FavoriteTeamTable {
            const val name: String = "tbl_favorite_team"

            object Column {
                const val id: String = "id"
                const val teamId: String = "teamId"
                const val teamName: String = "teamName"
                const val teamLogoUrl: String = "teamLogoUrl"
            }
        }
    }
}