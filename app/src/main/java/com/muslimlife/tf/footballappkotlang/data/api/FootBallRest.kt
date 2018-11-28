package com.muslimlife.tf.footballappkotlang.data.api

import com.muslimlife.tf.footballappkotlang.data.model.*
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface FootBallRest {

    @GET(FootBallRestConstant.lastFifteenMatchesEndPoint)
    fun getLastmatchByLeagueId(@Query(FootBallRestConstant.queryParamsId) id: String): Flowable<Events>

    @GET(FootBallRestConstant.nextFifteenMatchesEndPoint)
    fun getUpcomingMatchByLeagueId(@Query(FootBallRestConstant.queryParamsId) id: String): Flowable<Events>

    @GET(FootBallRestConstant.eventDetailsEndPoint)
    fun getMatchDetailById(@Query(FootBallRestConstant.queryParamsId) matchId: String): Flowable<Events>

    @GET(FootBallRestConstant.teamDetailsEndPoint)
    fun getTeamById(@Query(FootBallRestConstant.queryParamsId) id: String): Flowable<Teams>

    @GET(FootBallRestConstant.allLeaguesEndPoint)
    fun getAllLeagues(): Flowable<Leagues>

    @GET(FootBallRestConstant.searchEventEndPoint)
    fun searchMatchesByEventName(@Query(FootBallRestConstant.querySearchEventParam) query: String?): Flowable<SearchEvents>

    @GET(FootBallRestConstant.searchTeamEndPoint)
    fun searchTeamsByTeamName(@Query(FootBallRestConstant.querySearchTeamParam) query: String): Flowable<Teams>

    @GET(FootBallRestConstant.allTeamsEndPoint)
    fun getAllTeamsByLeagueId(@Query(FootBallRestConstant.queryParamsId) id: String): Flowable<Teams>

    @GET(FootBallRestConstant.allPlayersEndPoint)
    fun getAllPlayersByTeamId(@Query(FootBallRestConstant.queryParamsId) id: String?): Flowable<FootBallPlayerList>
}