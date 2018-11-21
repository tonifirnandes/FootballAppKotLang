package com.muslimlife.tf.footballappkotlang.features.detail

import android.content.Context
import com.muslimlife.tf.footballappkotlang.data.model.Event
import com.muslimlife.tf.footballappkotlang.data.model.FavoriteMatch
import com.muslimlife.tf.footballappkotlang.generics.BaseMvpPresenter
import com.muslimlife.tf.footballappkotlang.generics.BaseView

interface MatchScheduleDetailContract {
    interface View : BaseView {
        fun onGetHomeTeamBadgeSuccess(url: String)
        fun onGetAwayTeamBadgeFailed()
        fun onGetAwayTeamBadgeSuccess(url: String)
        fun onGetHomeTeamBadgeFailed()
        fun onSaveFavoriteMatchLoading()
        fun onSaveFavoriteMatchSuccess()
        fun onSaveFavoriteMatchFailed()
        fun onSetFavoriteView(isFavorite: Boolean)
        fun onUnFavoriteMatchLoading()
        fun onUnFavoriteMatchSuccess()
        fun onUnFavoriteMatchFailed()
        fun onSetupViewFailed(isRetryAble: Boolean)
        fun onSetupViewSuccess(eventLoaded: Event)
        fun onGetEventDetailsByIdLoading()
        fun onGetEventDetailsByIdFailed()
        fun showDetailActivityActionLoading()
        fun hideDetailActivityActionLoading()
    }

    interface Presenter : BaseMvpPresenter<MatchScheduleDetailContract.View> {
        fun setupView(event: Event?, favoriteEvent: FavoriteMatch?)
        fun getEventDetailsById(eventId: String)
        fun getHomeTeamBadge(id: String)
        fun getAwayTeamBadge(id: String)
        fun saveFavoriteMatch(event: Event?, context: Context?)
        fun isFavoriteMatch(matchId: String, context: Context?)
        fun unFavoriteMatch(matchId: String?, context: Context?)
    }
}