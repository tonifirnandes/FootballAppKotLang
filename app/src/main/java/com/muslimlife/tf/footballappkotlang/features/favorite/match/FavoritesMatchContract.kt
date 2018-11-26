package com.muslimlife.tf.footballappkotlang.features.favorite.match

import android.content.Context
import com.muslimlife.tf.footballappkotlang.data.model.local_db.FavoriteMatch
import com.muslimlife.tf.footballappkotlang.generics.BaseMvpPresenter
import com.muslimlife.tf.footballappkotlang.generics.BaseView

interface FavoritesMatchContract {
    interface View : BaseView {
        fun showGetFavoritesMatchLoading()
        fun onGetFavoritesMatchSuccess(favoriteMatchList: List<FavoriteMatch>)
        fun onGetFavoritesMatchFailed()
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun getSavedFavoritesMatchLocally(context: Context?)
    }
}