package com.muslimlife.tf.footballappkotlang.features.team.detail.player.list

import com.muslimlife.tf.footballappkotlang.data.api.FootBallRest
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import com.muslimlife.tf.footballappkotlang.extensions.rx.BaseSchedulerProvider
import com.muslimlife.tf.footballappkotlang.generics.BasePresenter
import io.reactivex.disposables.CompositeDisposable

class TeamPlayerPresenter(private val scheduler: BaseSchedulerProvider) : TeamPlayerContract.Presenter,
    BasePresenter<TeamPlayerContract.View>() {

    private var compositeDisposable = CompositeDisposable()
    private var service: FootBallRest = FootBallRestService.instance()

    override fun getTeamPlayersByTeamId(teamId: String) {
        view?.showGetTeamPlayersLoading()
        compositeDisposable.add(
            service.getAllPlayersByTeamId(teamId)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribe({
                    view?.onGetTeamPlayersSuccess(it.player)
                }, {
                    view?.onGetTeamPlayersFailed()
                })
        )
    }

}