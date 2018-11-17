package com.muslimlife.tf.footballappkotlang.features.home.list

import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FootBallMatchesSchedulePresenter(private val matchesScheduleView: FootBallMatchScheduleFragment)
    : FootBallMatchesScheduleContract.Presenter {

    private var compositeDisposable = CompositeDisposable()
    private var service = FootBallRestService.instance()

    override fun getMatchesScheduleByType(
        scheduleType: Int?,
        leagueId: String
    ) {
        when(scheduleType) {
            0 -> getLastFifteenSoccerMatchByLeagueId(leagueId)
            1 -> getNextFifteenSoccerMatchByLeagueId(leagueId)
            else -> throw UnsupportedOperationException()
        }
    }

    private fun getLastFifteenSoccerMatchByLeagueId(id: String) {
        matchesScheduleView.showGetMatchesScheduleLoading()
        compositeDisposable.add(service.getLastmatch(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                matchesScheduleView.onGetMatchesScheduleSuccessed(it.events)
            }, {
                matchesScheduleView.onGetMatchesScheduleFailed()
            }))
    }

    private fun getNextFifteenSoccerMatchByLeagueId(id: String) {
        matchesScheduleView.showGetMatchesScheduleLoading()
        compositeDisposable.add(service.getUpcomingMatch(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                matchesScheduleView.onGetMatchesScheduleSuccessed(it.events)
            }, {
                matchesScheduleView.onGetMatchesScheduleFailed()
            }))
    }
}