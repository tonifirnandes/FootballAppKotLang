package com.muslimlife.tf.footballappkotlang.data.db

import android.content.Context
import android.database.sqlite.SQLiteException
import com.muslimlife.tf.footballappkotlang.data.model.FavoriteMatch
import com.muslimlife.tf.footballappkotlang.extensions.localDb
import io.reactivex.Observable
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class FavoriteMatchLocalDb(private val context: Context) {

    fun create(data: FavoriteMatch): Observable<Long>{
        return Observable.create<Long>{
            try {
                context.localDb.use {
                    val createdId = insert(DbConstant.Companion.FavoriteMatchTable.name,
                        DbConstant.Companion.FavoriteMatchTable.Column.matchId to data.matchId,
                        DbConstant.Companion.FavoriteMatchTable.Column.matchName to data.matchName,
                        DbConstant.Companion.FavoriteMatchTable.Column.matchDate to data.matchDate,
                        DbConstant.Companion.FavoriteMatchTable.Column.homeTeamName to data.homeTeamName,
                        DbConstant.Companion.FavoriteMatchTable.Column.homeTeamScore to data.homeTeamScore,
                        DbConstant.Companion.FavoriteMatchTable.Column.awayTeamName to data.awayTeamName,
                        DbConstant.Companion.FavoriteMatchTable.Column.awayTeamScore to data.awayTeamScore)
                        it.onNext(createdId)
                    }
            } catch (e: SQLiteException) {
                it.onError(e)
            }
        }
    }

    fun delete(matchId: String): Observable<Int> {
        return Observable.create<Int>{
            try {
                context.localDb.use {
                    val affectedRows = delete(DbConstant.Companion.FavoriteMatchTable.name,
                            "${DbConstant.Companion.FavoriteMatchTable.Column.matchId} = {match_id}",
                            "match_id" to matchId
                        )
                    it.onNext(affectedRows)
                }
            } catch (e: SQLiteException) {
                it.onError(e)
            }
        }
    }

    fun isFavorite(matchId: String): Observable<Boolean>{
        return Observable.create<Boolean>{
            try {
                context.localDb.use {
                    val result = select(DbConstant.Companion.FavoriteMatchTable.name)
                        .whereArgs("(${DbConstant.Companion.FavoriteMatchTable.Column.matchId} = {match_id})",
                            "match_id" to matchId)
                    it.onNext( result.parseList(classParser<FavoriteMatch>()).isNotEmpty())
                }
            } catch (e: SQLiteException) {
                it.onError(e)
            }
        }
    }

    fun readAll(): Observable<List<FavoriteMatch>>{
        return Observable.create<List<FavoriteMatch>>{
            try {
                context.localDb.use {
                    val result = select(DbConstant.Companion.FavoriteMatchTable.name).parseList(classParser<FavoriteMatch>())
                    it.onNext( result.sortedByDescending { it -> it.id } )
                }
            } catch (e: SQLiteException) {
                it.onError(e)
            }
        }
    }

}