package com.muslimlife.tf.footballappkotlang.data.db

import android.content.Context
import android.database.sqlite.SQLiteException
import com.muslimlife.tf.footballappkotlang.data.model.local_db.FavoriteTeam
import com.muslimlife.tf.footballappkotlang.extensions.localDb
import io.reactivex.Observable
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class FavoriteTeamLocalDb(private val context: Context) {
    fun create(data: FavoriteTeam): Observable<Long> {
        return Observable.create<Long> {
            try {
                context.localDb.use {
                    val createdId = insert(
                        DbConstant.Companion.FavoriteTeamTable.name,
                        DbConstant.Companion.FavoriteTeamTable.Column.teamId to data.teamId,
                        DbConstant.Companion.FavoriteTeamTable.Column.teamName to data.teamName,
                        DbConstant.Companion.FavoriteTeamTable.Column.teamLogoUrl to data.teamLogoUrl
                    )
                    it.onNext(createdId)
                }
            } catch (e: SQLiteException) {
                it.onError(e)
            }
        }
    }

    fun delete(teamId: String): Observable<Int> {
        return Observable.create<Int> {
            try {
                context.localDb.use {
                    val affectedRows = delete(
                        DbConstant.Companion.FavoriteTeamTable.name,
                        "${DbConstant.Companion.FavoriteTeamTable.Column.teamId} = {team_id}",
                        "team_id" to teamId
                    )
                    it.onNext(affectedRows)
                }
            } catch (e: SQLiteException) {
                it.onError(e)
            }
        }
    }

    fun isFavorite(teamId: String): Observable<Boolean> {
        return Observable.create<Boolean> {
            try {
                context.localDb.use {
                    val result = select(DbConstant.Companion.FavoriteTeamTable.name)
                        .whereArgs(
                            "(${DbConstant.Companion.FavoriteTeamTable.Column.teamId} = {team_id})",
                            "team_id" to teamId
                        )
                    it.onNext(result.parseList(classParser<FavoriteTeam>()).isNotEmpty())
                }
            } catch (e: SQLiteException) {
                it.onError(e)
            }
        }
    }

    fun readAll(): Observable<List<FavoriteTeam>> {
        return Observable.create<List<FavoriteTeam>> {
            try {
                context.localDb.use {
                    val result =
                        select(DbConstant.Companion.FavoriteTeamTable.name).parseList(classParser<FavoriteTeam>())
                    it.onNext(result.sortedByDescending { (id) -> id }) //sorted by latest favorite team saved
                }
            } catch (e: SQLiteException) {
                it.onError(e)
            }
        }
    }
}