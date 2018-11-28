package com.muslimlife.tf.footballappkotlang.features.home

import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestConstant
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import com.muslimlife.tf.footballappkotlang.data.preference.SharedPrefs
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
                    SharedPrefs.leaguesData = it
                    view?.onGetAllLeaguesSuccess(it.leagues.filter { league ->
                        league.category ==
                                FootBallRestConstant.apiSoccerCategory
                    })
                }, {
                    view?.onGetAllLeaguesFailed()
                })
        )
    }

    override fun findMatches(eventSearched: String) {
        view?.showFindMatchesLoading()
        //force remove previous action
        compositeDisposable.clear()
        compositeDisposable.add(
            service.searchMatchesByEventName(eventSearched)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val found = it.event.filter { event -> event.category == FootBallRestConstant.apiSoccerCategory }
                    if (found.isEmpty()) {
                        view?.onNotFoundMatches()
                    } else {
                        view?.onFoundMathces(found)
                    }

                }, {
                    view?.onNotFoundMatches()
                })
        )
    }

}