package com.muslimlife.tf.footballappkotlang.features.detail

import com.muslimlife.tf.footballappkotlang.data.api.FootBallRest
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MatchScheduleDetailPresenter(private val matchScheduleView: MatchScheduleDetailActivity) : MatchScheduleDetailContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var service: FootBallRest = FootBallRestService.instance()

    override fun getHomeTeamBadge(id: String) {
        compositeDisposable.add(service.getTeam(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                matchScheduleView.onGetHomeTeamBadgeSuccessed(it.teams[0].badgeUrl)
            }, {
                matchScheduleView.onGetHomeTeamBadgeFailed()
            }))
    }

    override fun getAwayTeamBadge(id: String) {
        compositeDisposable.add(service.getTeam(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                matchScheduleView.onGetAwayTeamBadgeSuccessed(it.teams[0].badgeUrl)
            }, {
                matchScheduleView.onGetAwayTeamBadgeFailed()
            }))
    }
}