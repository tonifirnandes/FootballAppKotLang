package com.muslimlife.tf.footballappkotlang.features.home

import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestConstant
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import com.muslimlife.tf.footballappkotlang.generics.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomePresenter : HomeContract.Presenter, BasePresenter<HomeContract.View>() {

    //dependecy injection
    private val compositeDisposable = CompositeDisposable()
    private val service = FootBallRestService.instance()

    override fun getAllLeagues() {
        view?.showGetAllLeaguesLoading()
        //repository pattern is a good idea for futher refactoring
        compositeDisposable.add(
            service.getAllLeagues()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view?.onGetAllLeaguesSuccessed(it.leagues.filter { league ->
                        league.category ==
                                FootBallRestConstant.apiSoccerCategory
                    })
                }, {
                    view?.onGetAllLeaguesFailed()
                })
        )
    }

}