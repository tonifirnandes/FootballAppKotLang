package com.muslimlife.tf.footballappkotlang.features.team.detail

import android.content.Context
import com.muslimlife.tf.footballappkotlang.data.model.Team
import com.muslimlife.tf.footballappkotlang.data.model.local_db.FavoriteTeam
import com.muslimlife.tf.footballappkotlang.generics.BaseMvpPresenter
import com.muslimlife.tf.footballappkotlang.generics.BaseView

interface TeamDetailContract {
    interface View : BaseView {
        fun onSetupViewSuccess(teamLoaded: Team)
        fun onSetupViewFailed(isRetryAble: Boolean)
        fun onSaveFavoriteTeamLoading()
        fun onSaveFavoriteTeamSuccess()
        fun onSaveFavoriteTeamFailed()
        fun onSetFavoriteView(isFavorite: Boolean)
        fun onUnFavoriteTeamLoading()
        fun onUnFavoriteTeamSuccess()
        fun onUnFavoriteTeamFailed()
        fun onGetTeamDetailsByIdLoading()
        fun onGetTeamDetailsByIdFailed()
        fun showDetailActivityActionLoading()
        fun hideDetailActivityActionLoading()
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun setupView(team: Team?, favoriteTeam: FavoriteTeam?)
        fun getTeamDetailsById(teamId: String)
        fun saveFavoriteTeam(team: Team?, context: Context?)
        fun isFavoriteTeam(teamId: String, context: Context?)
        fun unFavoriteTeam(teamId: String?, context: Context?)
    }
}