package com.muslimlife.tf.footballappkotlang.features.detail

import android.content.Context
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRest
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import com.muslimlife.tf.footballappkotlang.data.db.FavoriteMatchLocalDb
import com.muslimlife.tf.footballappkotlang.data.model.Event
import com.muslimlife.tf.footballappkotlang.data.model.FavoriteMatch
import com.muslimlife.tf.footballappkotlang.generics.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MatchScheduleDetailPresenter : MatchScheduleDetailContract.Presenter,
    BasePresenter<MatchScheduleDetailContract.View>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var service: FootBallRest = FootBallRestService.instance()

    override fun setupView(event: Event?, favoriteEvent: FavoriteMatch?) {
        if (event != null) {
            view?.onSetupViewSuccessed(event)
            return
        }

        if (favoriteEvent != null) {
            getEventDetailsById(favoriteEvent.matchId)
            return
        }

        view?.onSetupViewFailed(false)
    }

    override fun getEventDetailsById(eventId: String) {
        view?.showDetailActivityActionLoading()
        compositeDisposable.add(
            service.getMatchDetail(eventId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view?.onSetupViewSuccessed(it.events[0])
                }, {
                    view?.onSetupViewFailed(true)
                })
        )
    }

    override fun getHomeTeamBadge(id: String) {
        compositeDisposable.add(
            service.getTeam(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view?.onGetHomeTeamBadgeSuccessed(it.teams[0].badgeUrl)
                }, {
                    view?.onGetHomeTeamBadgeFailed()
                })
        )
    }

    override fun getAwayTeamBadge(id: String) {
        compositeDisposable.add(
            service.getTeam(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view?.onGetAwayTeamBadgeSuccessed(it.teams[0].badgeUrl)
                }, {
                    view?.onGetAwayTeamBadgeFailed()
                })
        )
    }

    override fun saveFavoriteMatch(event: Event?, context: Context?) {
        if (context == null || event == null) {
            view?.onSaveFavoriteMatchFailed()
            return
        }
        view?.onSaveFavoriteMatchLoading()
        compositeDisposable.add(
            FavoriteMatchLocalDb(context).create(
                FavoriteMatch(
                    id = null,
                    matchId = event.id,
                    matchName = event.name,
                    matchDate = event.date,
                    homeTeamName = event.homeTeamName,
                    homeTeamScore = event.homeScoreNumber,
                    awayTeamName = event.awayTeamName,
                    awayTeamScore = event.awayScoreNumber
                )
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view?.onSetFavoriteView(true)
                    view?.onSaveFavoriteMatchSuccess()
                }, {
                    view?.onSaveFavoriteMatchFailed()
                })
        )
    }

    override fun isFavoriteMatch(matchId: String, context: Context?) {
        if (context == null) {
            return
        }
        compositeDisposable.add(
            FavoriteMatchLocalDb(context).isFavorite(matchId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    view?.onSetFavoriteView(it)
                })
    }

    override fun unFavoriteMatch(matchId: String?, context: Context?) {
        if (context == null || matchId == null) {
            view?.onUnFavoriteMatchFailed()
            return
        }
        view?.onUnFavoriteMatchLoading()
        compositeDisposable.add(
            FavoriteMatchLocalDb(context).delete(matchId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view?.onSetFavoriteView(false)
                    view?.onUnFavoriteMatchSuccess()
                }, {
                    view?.onUnFavoriteMatchFailed()
                })
        )
    }
}