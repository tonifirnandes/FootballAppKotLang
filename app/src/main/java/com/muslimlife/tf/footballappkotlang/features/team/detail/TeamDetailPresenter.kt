package com.muslimlife.tf.footballappkotlang.features.team.detail

import android.content.Context
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRest
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import com.muslimlife.tf.footballappkotlang.data.db.FavoriteTeamLocalDb
import com.muslimlife.tf.footballappkotlang.data.model.Team
import com.muslimlife.tf.footballappkotlang.data.model.local_db.FavoriteTeam
import com.muslimlife.tf.footballappkotlang.extensions.rx.BaseSchedulerProvider
import com.muslimlife.tf.footballappkotlang.generics.BasePresenter
import io.reactivex.disposables.CompositeDisposable

class TeamDetailPresenter(private val scheduler: BaseSchedulerProvider) :
    TeamDetailContract.Presenter,
    BasePresenter<TeamDetailContract.View>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var service: FootBallRest = FootBallRestService.instance()

    override fun setupView(team: Team?, favoriteTeam: FavoriteTeam?) {
        if (team != null) {
            view?.onSetupViewSuccess(team)
            return
        }

        if (favoriteTeam != null) {
            getTeamDetailsById(favoriteTeam.teamId)
            return
        }

        view?.onSetupViewFailed(false)
    }

    override fun getTeamDetailsById(teamId: String) {
        view?.showDetailActivityActionLoading()
        compositeDisposable.add(
            service.getTeamById(teamId)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribe({
                    view?.onSetupViewSuccess(it.teams[0])
                }, {
                    view?.onSetupViewFailed(true)
                })
        )
    }

    override fun saveFavoriteTeam(team: Team?, context: Context?) {
        if (context == null || team == null) {
            view?.onSaveFavoriteTeamFailed()
            return
        }
        view?.onSaveFavoriteTeamLoading()
        compositeDisposable.add(
            FavoriteTeamLocalDb(context).create(
                FavoriteTeam(
                    id = null,
                    teamId = team.id,
                    teamName = team.name,
                    teamLogoUrl = team.badgeUrl
                )
            )
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribe({
                    view?.onSetFavoriteView(true)
                    view?.onSaveFavoriteTeamSuccess()
                }, {
                    view?.onSaveFavoriteTeamFailed()
                })
        )
    }

    override fun isFavoriteTeam(teamId: String, context: Context?) {
        if (context == null) {
            return
        }
        compositeDisposable.add(
            FavoriteTeamLocalDb(context).isFavorite(teamId)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribe {
                    view?.onSetFavoriteView(it)
                })
    }

    override fun unFavoriteTeam(teamId: String?, context: Context?) {
        if (context == null || teamId == null) {
            view?.onUnFavoriteTeamFailed()
            return
        }
        view?.onUnFavoriteTeamLoading()
        compositeDisposable.add(
            FavoriteTeamLocalDb(context).delete(teamId)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribe({
                    view?.onSetFavoriteView(false)
                    view?.onUnFavoriteTeamSuccess()
                }, {
                    view?.onUnFavoriteTeamFailed()
                })
        )
    }

}