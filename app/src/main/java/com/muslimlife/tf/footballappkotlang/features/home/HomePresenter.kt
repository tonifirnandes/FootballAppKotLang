package com.muslimlife.tf.footballappkotlang.features.home

import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestConstant
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomePresenter(private val homeView: HomeContract.View): HomeContract.Presenter {

    //dependecy injection
    private val compositeDisposable = CompositeDisposable()
    private val service = FootBallRestService.instance()

    override fun getAllLeagues() {
        homeView.showGetAllLeaguesLoading()
        //repository pattern is a good idea for futher refactoring
        compositeDisposable.add(service.getAllLeagues()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                homeView.onGetAllLeaguesSuccessed(it.leagues.filter { league -> league.category ==
                        FootBallRestConstant.apiSoccerCategory })
            }, {
                homeView.onGetAllLeaguesFailed()
            }))
    }

}