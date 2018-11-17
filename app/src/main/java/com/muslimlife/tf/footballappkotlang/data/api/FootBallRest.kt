package com.muslimlife.tf.footballappkotlang.data.api

import com.muslimlife.tf.footballappkotlang.data.model.Events
import com.muslimlife.tf.footballappkotlang.data.model.Leagues
import com.muslimlife.tf.footballappkotlang.data.model.Teams
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface FootBallRest {

    @GET(FootBallRestConstant.lastFifteenMatchesEndPoint)
    fun getLastmatch(@Query(FootBallRestConstant.queryParamsId) id:String) : Flowable<Events>

    @GET(FootBallRestConstant.nextFifteenMatchesEndPoint)
    fun getUpcomingMatch(@Query(FootBallRestConstant.queryParamsId) id:String) : Flowable<Events>

    @GET(FootBallRestConstant.eventDetailsEndPoint)
    fun getMatchDetail(@Query(FootBallRestConstant.queryParamsId) matchId: String): Flowable<Events>

    @GET(FootBallRestConstant.teamDetailsEndPoint)
    fun getTeam(@Query(FootBallRestConstant.queryParamsId) id:String) : Flowable<Teams>

    @GET(FootBallRestConstant.allLeaguesEndPoint)
    fun getAllLeagues() : Flowable<Leagues>
}