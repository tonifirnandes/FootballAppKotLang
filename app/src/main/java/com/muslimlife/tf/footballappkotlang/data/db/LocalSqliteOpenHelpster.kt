package com.muslimlife.tf.footballappkotlang.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class LocalSqliteOpenHelpster(context: Context) : ManagedSQLiteOpenHelper(
    context, DbConstant.name, null,
    DbConstant.version
) {

    companion object {

        private var instance: LocalSqliteOpenHelpster? = null

        @Synchronized
        fun instance(context: Context): LocalSqliteOpenHelpster {
            if (instance == null) {
                instance = LocalSqliteOpenHelpster(context.applicationContext)
            }
            return instance as LocalSqliteOpenHelpster
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            DbConstant.Companion.FavoriteMatchTable.name, true,
            DbConstant.Companion.FavoriteMatchTable.Column.id to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            DbConstant.Companion.FavoriteMatchTable.Column.matchId to TEXT + UNIQUE,
            DbConstant.Companion.FavoriteMatchTable.Column.matchName to TEXT,
            DbConstant.Companion.FavoriteMatchTable.Column.matchDate to TEXT,
            DbConstant.Companion.FavoriteMatchTable.Column.matchTime to TEXT,
            DbConstant.Companion.FavoriteMatchTable.Column.homeTeamName to TEXT,
            DbConstant.Companion.FavoriteMatchTable.Column.homeTeamScore to TEXT,
            DbConstant.Companion.FavoriteMatchTable.Column.awayTeamName to TEXT,
            DbConstant.Companion.FavoriteMatchTable.Column.awayTeamScore to TEXT
        )

        db?.createTable(
            DbConstant.Companion.FavoriteTeamTable.name, true,
            DbConstant.Companion.FavoriteTeamTable.Column.id to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            DbConstant.Companion.FavoriteTeamTable.Column.teamId to TEXT + UNIQUE,
            DbConstant.Companion.FavoriteTeamTable.Column.teamName to TEXT,
            DbConstant.Companion.FavoriteTeamTable.Column.teamLogoUrl to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(DbConstant.Companion.FavoriteMatchTable.name, true)
        db?.dropTable(DbConstant.Companion.FavoriteTeamTable.name, true)
    }

}