package com.muslimlife.tf.footballappkotlang.features.team.list

import com.muslimlife.tf.footballappkotlang.data.api.FootBallRest
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import com.muslimlife.tf.footballappkotlang.extensions.rx.BaseSchedulerProvider
import com.muslimlife.tf.footballappkotlang.generics.BasePresenter
import io.reactivex.disposables.CompositeDisposable

class TeamsPresenter(private val scheduler: BaseSchedulerProvider) : TeamsContract.Presenter,
    BasePresenter<TeamsContract.View>() {

    private var compositeDisposable = CompositeDisposable()
    private var service: FootBallRest = FootBallRestService.instance()

    override fun getTeamsByLeagueId(leagueId: String) {
        view?.showGetTeamsLoading()
        compositeDisposable.add(
            service.getAllTeamsByLeagueId(leagueId)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribe({
                    view?.onGetTeamsSuccess(it.teams)
                }, {
                    view?.onGetTeamsFailed()
                })
        )
    }

}