package com.muslimlife.tf.footballappkotlang.features.favorite

import android.content.Context
import com.muslimlife.tf.footballappkotlang.data.model.FavoriteMatch

interface FavoritesMatchContract {
    interface View{
        fun showGetFavoritesMatchLoading()
        fun onGetFavoritesMatchSuccessed(favoriteMatchList: List<FavoriteMatch>)
        fun onGetFavoritesMatchFailed()
    }

    interface Presenter{
        fun getSavedFavoritesMatchLocally(context: Context?)
    }
}