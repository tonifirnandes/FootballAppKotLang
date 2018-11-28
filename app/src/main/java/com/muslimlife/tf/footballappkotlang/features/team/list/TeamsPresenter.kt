package com.muslimlife.tf.footballappkotlang.features.team.list

import com.muslimlife.tf.footballappkotlang.data.api.FootBallRest
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestConstant
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

    override fun findTeams(searchedTeamName: String) {
        view?.showSearchTeamsLoading()
        //force remove previous action
        compositeDisposable.clear()
        compositeDisposable.add(
            service.searchTeamsByTeamName(searchedTeamName)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribe({
                    val found = it.teams.filter { team -> team.category == FootBallRestConstant.apiSoccerCategory }
                    if (found.isEmpty()) {
                        view?.onNotFoundTeams()
                    } else {
                        view?.onFoundTeams(found)
                    }

                }, {
                    view?.onNotFoundTeams()
                })
        )
    }

}