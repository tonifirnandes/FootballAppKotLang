package com.muslimlife.tf.footballappkotlang.data.db

class DbConstant {
    companion object {
        const val name = "footballappkotlang.db"
        const val version = 1

        object FavoriteMatchTable {
            const val name = "tbl_favorite_match"

            object Column {
                const val id = "id"
                const val matchId = "matchId"
                const val matchName = "matchName"
                const val matchDate = "matchDate"
                const val homeTeamName = "homeTeamName"
                const val homeTeamScore = "homeTeamScore"
                const val awayTeamName = "awayTeamName"
                const val awayTeamScore = "awayTeamScore"
            }
        }
    }
}