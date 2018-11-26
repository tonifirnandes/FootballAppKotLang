package com.muslimlife.tf.footballappkotlang.features.favorite.team

import android.content.Context
import com.muslimlife.tf.footballappkotlang.data.model.local_db.FavoriteTeam
import com.muslimlife.tf.footballappkotlang.generics.BaseMvpPresenter
import com.muslimlife.tf.footballappkotlang.generics.BaseView

interface FavoriteTeamContract {
    interface View : BaseView {
        fun showGetFavoritesTeamLoading()
        fun onGetFavoritesTeamSuccess(favoriteTeamList: List<FavoriteTeam>)
        fun onGetFavoritesTeamFailed()
    }

    interface Presenter : BaseMvpPresenter<FavoriteTeamContract.View> {
        fun getSavedFavoritesMatchLocally(context: Context?)
    }
}