package com.muslimlife.tf.footballappkotlang.features.detail

import android.content.Context
import com.muslimlife.tf.footballappkotlang.data.model.Event
import com.muslimlife.tf.footballappkotlang.data.model.FavoriteMatch

interface MatchScheduleDetailContract {
    interface View{
        fun onGetHomeTeamBadgeSuccessed(url: String)
        fun onGetAwayTeamBadgeFailed()
        fun onGetAwayTeamBadgeSuccessed(url: String)
        fun onGetHomeTeamBadgeFailed()
        fun onSaveFavoriteMatchLoading()
        fun onSaveFavoriteMatchFailed()
        fun onSetFavoriteView(isFavorite: Boolean)
        fun onUnFavoriteMatchLoading()
        fun onUnFavoriteMatchFailed()
        fun onSetupViewFailed(isRetryAble: Boolean)
        fun onSetupViewSuccessed(eventLoaded: Event)
        fun onGetEventDetailsByIdLoading()
        fun onGetEventDetailsByIdFailed()
        fun showDetailActivityActionLoading()
        fun hideDetailActivityActionLoading()
    }

    interface Presenter {
        fun setupView(event: Event?, favoriteEvent: FavoriteMatch?)
        fun getEventDetailsById(eventId: String)
        fun getHomeTeamBadge(id: String)
        fun getAwayTeamBadge(id: String)
        fun saveFavoriteMatch(event: Event, context: Context?)
        fun isFavoriteMatch(matchId: String, context: Context?)
        fun unFavoriteMatch(matchId: String, context: Context?)
    }
}