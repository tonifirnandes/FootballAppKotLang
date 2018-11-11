package com.muslimlife.tf.footballappkotlang.features.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRest
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import com.muslimlife.tf.footballappkotlang.data.model.Event
import com.muslimlife.tf.footballappkotlang.extensions.Utils
import com.muslimlife.tf.footballappkotlang.extensions.adjustTimePattern
import com.muslimlife.tf.footballappkotlang.extensions.splitted
import com.rahmat.app.footballclub.extensions.hide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.match_schedule_detail_activity.*

class MatchScheduleDetailActivity : AppCompatActivity() {

    private lateinit var compositeDisposable: CompositeDisposable

    private lateinit var service: FootBallRest

    companion object {
        const val arg_match_bundle_key = "match"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
        service = FootBallRestService.instance()
        setContentView(R.layout.match_schedule_detail_activity)
        val event = intent.getParcelableExtra<Event>(arg_match_bundle_key)
        initView(event)
        supportActionBar?.title = event.name
        getHomeTeamBadge(event.homeTeamId)
        getAwayTeamBadge(event.awayTeamId)
    }

    fun initView(event: Event){
        tv_match_date.text = event.date?.adjustTimePattern(Utils.originEventDateTimeFormat,
            Utils.matchEventDateTimeFormat)
        tv_home_team_name.text = event.homeTeamName
        tv_home_team_score.text = event.homeScoreNumber
        tv_away_team_name.text = event.awayTeamName
        tv_away_team_score.text = event.awayScoreNumber

        tv_home_team_goals_summary.text = event.homeGoalDetails?.splitted()
        tv_away_team_goals_summary.text= event.awayGoalDetails?.splitted()

        tv_home_team_shot_number.text = event.homeTeamShotsNumber
        tv_away_team_shot_number.text = event.awayTeamShotsNumber

        tv_home_team_goalkeepers.text = event.homeLineupGoalkeeper?.splitted()
        tv_away_team_goalkeepers.text = event.awayLineupGoalkeeper?.splitted()

        tv_home_team_defenses.text = event.homeLineupDefense?.splitted()
        tv_away_team_defenses.text = event.awayLineupDefense?.splitted()

        tv_home_team_midfields.text = event.homeLineupMidfield?.splitted()
        tv_away_team_midfields.text = event.awayLineupMidfield?.splitted()

        tv_home_team_forwards.text = event.homeLineupForward?.splitted()
        tv_away_team_forwards.text = event.awayLineupForward?.splitted()

        tv_home_team_subtitutes.text = event.homeLineupSubstitutes?.splitted()
        tv_away_team_subtitutes.text = event.awayLineupSubstitutes?.splitted()

    }

    fun getHomeTeamBadge(id: String) {
        compositeDisposable.add(service.getTeam(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                getHomeTeamBadgeSuccessed(it.teams[0].badgeUrl)
            }, {
                getHomeTeamBadgeFailed()
            }))
    }

    fun getAwayTeamBadge(id: String) {
        compositeDisposable.add(service.getTeam(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                getAwayTeamBadgeSuccessed(it.teams[0].badgeUrl)
            }, {
                getAwayTeamBadgeFailed()
            }))
    }

    fun getHomeTeamBadgeSuccessed(url: String) {
        pb_home_team_logo.hide()
        Glide.with(iv_home_team_logo)
            .load(url)
            .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
            .into(iv_home_team_logo)
    }

    fun getAwayTeamBadgeFailed() {
        pb_home_team_logo.hide()
    }

    fun getAwayTeamBadgeSuccessed(url: String) {
        pb_away_team_logo.hide()
        Glide.with(iv_away_team_logo)
            .load(url)
            .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
            .into(iv_away_team_logo)
    }

    fun getHomeTeamBadgeFailed() {
        pb_away_team_logo.hide()
    }
}