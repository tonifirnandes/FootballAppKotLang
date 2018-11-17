package com.muslimlife.tf.footballappkotlang.features.favorite

import android.content.Context
import com.muslimlife.tf.footballappkotlang.data.model.FavoriteMatch
import com.muslimlife.tf.footballappkotlang.generics.BaseMvpPresenter
import com.muslimlife.tf.footballappkotlang.generics.BaseView

interface FavoritesMatchContract {
    interface View: BaseView{
        fun showGetFavoritesMatchLoading()
        fun onGetFavoritesMatchSuccessed(favoriteMatchList: List<FavoriteMatch>)
        fun onGetFavoritesMatchFailed()
    }

    interface Presenter: BaseMvpPresenter<FavoritesMatchContract.View>{
        fun getSavedFavoritesMatchLocally(context: Context?)
    }
}