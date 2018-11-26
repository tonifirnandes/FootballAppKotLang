package com.muslimlife.tf.footballappkotlang.features.home.list

import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import com.muslimlife.tf.footballappkotlang.generics.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FootBallMatchesSchedulePresenter
    : FootBallMatchesScheduleContract.Presenter, BasePresenter<FootBallMatchesScheduleContract.View>() {

    private var compositeDisposable = CompositeDisposable()
    private var service = FootBallRestService.instance()

    override fun getMatchesScheduleByType(
        scheduleType: Int?,
        leagueId: String
    ) {
        when (scheduleType) {
            0 -> getLastFifteenSoccerMatchByLeagueId(leagueId)
            1 -> getNextFifteenSoccerMatchByLeagueId(leagueId)
            else -> throw UnsupportedOperationException()
        }
    }

    private fun getLastFifteenSoccerMatchByLeagueId(id: String) {
        view?.showGetMatchesScheduleLoading()
        compositeDisposable.add(
            service.getLastmatchByLeagueId(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view?.onGetMatchesScheduleSuccess(it.events)
                }, {
                    view?.onGetMatchesScheduleFailed()
                })
        )
    }

    private fun getNextFifteenSoccerMatchByLeagueId(id: String) {
        view?.showGetMatchesScheduleLoading()
        compositeDisposable.add(
            service.getUpcomingMatchByLeagueId(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view?.onGetMatchesScheduleSuccess(it.events)
                }, {
                    view?.onGetMatchesScheduleFailed()
                })
        )
    }
}