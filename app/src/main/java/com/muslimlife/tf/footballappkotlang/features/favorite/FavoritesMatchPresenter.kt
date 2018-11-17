package com.muslimlife.tf.footballappkotlang.features.favorite

import android.content.Context
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import com.muslimlife.tf.footballappkotlang.data.db.FavoriteMatchLocalDb
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoritesMatchPresenter(private val favoritesMatchView: FavoritesMatchContract.View)
    : FavoritesMatchContract.Presenter {

    private var compositeDisposable = CompositeDisposable()

    override fun getSavedFavoritesMatchLocally(context: Context?) {
        if(context == null)  return favoritesMatchView.onGetFavoritesMatchFailed()
        favoritesMatchView.showGetFavoritesMatchLoading()
        compositeDisposable.add(
            FavoriteMatchLocalDb(context).readAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    favoritesMatchView.onGetFavoritesMatchSuccessed(it)
                }, {
                    favoritesMatchView.onGetFavoritesMatchFailed()
                }))

    }

}