package com.muslimlife.tf.footballappkotlang.features.favorite.team

import android.content.Context
import com.muslimlife.tf.footballappkotlang.data.db.FavoriteTeamLocalDb
import com.muslimlife.tf.footballappkotlang.generics.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoriteTeamsPresenter : FavoriteTeamContract.Presenter, BasePresenter<FavoriteTeamContract.View>() {

    private var compositeDisposable = CompositeDisposable()

    override fun getSavedFavoritesMatchLocally(context: Context?) {
        if (context == null) {
            view?.onGetFavoritesTeamFailed()
            return
        }
        view?.showGetFavoritesTeamLoading()
        compositeDisposable.add(
            FavoriteTeamLocalDb(context).readAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view?.onGetFavoritesTeamSuccess(it)
                }, {
                    view?.onGetFavoritesTeamFailed()
                })
        )
    }

}


//class FavoritesMatchPresenter
//    : FavoritesMatchContract.Presenter, BasePresenter<FavoritesMatchContract.View>() {
//
//    private var compositeDisposable = CompositeDisposable()
//
//    override fun getSavedFavoritesMatchLocally(context: Context?) {
//        if (context == null) {
//            view?.onGetFavoritesMatchFailed()
//            return
//        }
//        view?.showGetFavoritesMatchLoading()
//        compositeDisposable.add(
//            FavoriteMatchLocalDb(context).readAll()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe({
//                    view?.onGetFavoritesMatchSuccess(it)
//                }, {
//                    view?.onGetFavoritesMatchFailed()
//                })
//        )
//
//    }
//
//}