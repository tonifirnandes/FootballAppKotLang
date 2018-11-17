package com.muslimlife.tf.footballappkotlang.features.detail

import android.content.Context
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRest
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import com.muslimlife.tf.footballappkotlang.data.db.FavoriteMatchLocalDb
import com.muslimlife.tf.footballappkotlang.data.model.FavoriteMatch
import com.muslimlife.tf.footballappkotlang.data.model.Event

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MatchScheduleDetailPresenter(private val matchScheduleView: MatchScheduleDetailActivity) : MatchScheduleDetailContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var service: FootBallRest = FootBallRestService.instance()

    override fun setupView(event: Event?, favoriteEvent: FavoriteMatch?) {
        if(event != null) {
            matchScheduleView.onSetupViewSuccessed(event)
            return
        }

        if(favoriteEvent != null) {
            getEventDetailsById(favoriteEvent.matchId)
            return
        }

        matchScheduleView.onSetupViewFailed(false)
    }

    override fun getEventDetailsById(eventId: String) {
        matchScheduleView.showDetailActivityActionLoading()
        compositeDisposable.add(service.getMatchDetail(eventId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                matchScheduleView.onSetupViewSuccessed(it.events[0])
            }, {
                matchScheduleView.onSetupViewFailed(true)
            }))
    }

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

    override fun saveFavoriteMatch(event: Event, context: Context?) {
        if(context == null) return matchScheduleView.onSaveFavoriteMatchFailed()
        matchScheduleView.onSaveFavoriteMatchLoading()
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
                awayTeamScore = event.awayScoreNumber))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                matchScheduleView.onSetFavoriteView(true)
            }, {
                matchScheduleView.onSaveFavoriteMatchFailed()
            }))
    }

    override fun isFavoriteMatch(matchId: String, context: Context?) {
        if(context == null) return matchScheduleView.onSaveFavoriteMatchFailed()
        compositeDisposable.add(
            FavoriteMatchLocalDb(context).isFavorite(matchId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe{
                    matchScheduleView.onSetFavoriteView(it)
                })
    }

    override fun unFavoriteMatch(matchId: String, context: Context?) {
        if(context == null) return matchScheduleView.onSaveFavoriteMatchFailed()
        matchScheduleView.onUnFavoriteMatchLoading()
        compositeDisposable.add(
            FavoriteMatchLocalDb(context).delete(matchId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    matchScheduleView.onSetFavoriteView(false)
                }, {
                    matchScheduleView.onUnFavoriteMatchFailed()
                }))
    }
}