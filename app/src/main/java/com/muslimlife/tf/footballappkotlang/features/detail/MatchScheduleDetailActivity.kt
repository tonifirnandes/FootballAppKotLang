package com.muslimlife.tf.footballappkotlang.features.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.Event
import com.muslimlife.tf.footballappkotlang.extensions.Utils
import com.muslimlife.tf.footballappkotlang.extensions.adjustTimePattern
import com.muslimlife.tf.footballappkotlang.extensions.splitted
import com.rahmat.app.footballclub.extensions.hide
import kotlinx.android.synthetic.main.match_schedule_detail_activity.*

class MatchScheduleDetailActivity : AppCompatActivity(), MatchScheduleDetailContract.View {

    private val matchScheduleDetailPresenter: MatchScheduleDetailPresenter = MatchScheduleDetailPresenter(this)

    companion object {
        const val arg_match_bundle_key = "match"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.match_schedule_detail_activity)
        val event = intent.getParcelableExtra<Event>(arg_match_bundle_key)
        initView(event)
        supportActionBar?.title = event.name
        matchScheduleDetailPresenter.getHomeTeamBadge(event.homeTeamId)
        matchScheduleDetailPresenter.getAwayTeamBadge(event.awayTeamId)
    }

    override fun onGetHomeTeamBadgeSuccessed(url: String) {
        pb_home_team_logo.hide()
        Glide.with(iv_home_team_logo)
            .load(url)
            .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
            .into(iv_home_team_logo)
    }

    override fun onGetAwayTeamBadgeFailed() {
        pb_home_team_logo.hide()
    }

    override fun onGetAwayTeamBadgeSuccessed(url: String) {
        pb_away_team_logo.hide()
        Glide.with(iv_away_team_logo)
            .load(url)
            .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
            .into(iv_away_team_logo)
    }

    override fun onGetHomeTeamBadgeFailed() {
        pb_away_team_logo.hide()
    }

    private fun initView(event: Event){
        showMatchSummaryView(event)
        showMatchGoalsView(event)
        showMatchShotsView(event)
        showLineupsGoalKeeperView(event)
        showLineupsDefenseView(event)
        showLineupsMidfieldView(event)
        showLineupsForwardView(event)
        showLineupsSubtitutesView(event)
    }

    private fun showMatchSummaryView(event: Event) {
        tv_match_date.text = event.date?.adjustTimePattern(Utils.originEventDateTimeFormat,
            Utils.matchEventDateTimeFormat)
        tv_home_team_name.text = event.homeTeamName
        tv_home_team_score.text = event.homeScoreNumber
        tv_away_team_name.text = event.awayTeamName
        tv_away_team_score.text = event.awayScoreNumber
    }

    private fun showMatchGoalsView(event: Event) {
        tv_home_team_goals_summary.text = event.homeGoalDetails?.splitted()
        tv_away_team_goals_summary.text= event.awayGoalDetails?.splitted()

    }

    private fun showMatchShotsView(event: Event) {
        tv_home_team_shot_number.text = event.homeTeamShotsNumber
        tv_away_team_shot_number.text = event.awayTeamShotsNumber
    }

    private fun showLineupsGoalKeeperView(event: Event) {
        tv_home_team_goalkeepers.text = event.homeLineupGoalkeeper?.splitted()
        tv_away_team_goalkeepers.text = event.awayLineupGoalkeeper?.splitted()
    }

    private fun showLineupsDefenseView(event: Event){
        tv_home_team_defenses.text = event.homeLineupDefense?.splitted()
        tv_away_team_defenses.text = event.awayLineupDefense?.splitted()
    }

    private fun showLineupsMidfieldView(event: Event){
        tv_home_team_midfields.text = event.homeLineupMidfield?.splitted()
        tv_away_team_midfields.text = event.awayLineupMidfield?.splitted()
    }

    private fun showLineupsForwardView(event: Event){
        tv_home_team_forwards.text = event.homeLineupForward?.splitted()
        tv_away_team_forwards.text = event.awayLineupForward?.splitted()
    }

    private fun showLineupsSubtitutesView(event: Event){
        tv_home_team_subtitutes.text = event.homeLineupSubstitutes?.splitted()
        tv_away_team_subtitutes.text = event.awayLineupSubstitutes?.splitted()
    }
}