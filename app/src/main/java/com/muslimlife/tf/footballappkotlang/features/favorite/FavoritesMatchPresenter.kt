package com.muslimlife.tf.footballappkotlang.features.favorite

import android.content.Context
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import com.muslimlife.tf.footballappkotlang.data.db.FavoriteMatchLocalDb
import com.muslimlife.tf.footballappkotlang.generics.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoritesMatchPresenter
    : FavoritesMatchContract.Presenter, BasePresenter<FavoritesMatchContract.View>() {

    private var compositeDisposable = CompositeDisposable()

    override fun getSavedFavoritesMatchLocally(context: Context?) {
        if(context == null) {
            view?.onGetFavoritesMatchFailed()
            return
        }
        view?.showGetFavoritesMatchLoading()
        compositeDisposable.add(
            FavoriteMatchLocalDb(context).readAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view?.onGetFavoritesMatchSuccessed(it)
                }, {
                    view?.onGetFavoritesMatchFailed()
                }))

    }

}