package com.muslimlife.tf.footballappkotlang.features.detail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.Event
import com.muslimlife.tf.footballappkotlang.R.menu.detail_activity_options_menu
import com.muslimlife.tf.footballappkotlang.R.drawable.ic_add_to_favorites
import com.muslimlife.tf.footballappkotlang.R.drawable.ic_favorited
import com.muslimlife.tf.footballappkotlang.data.model.FavoriteMatch
import com.muslimlife.tf.footballappkotlang.extensions.*
import kotlinx.android.synthetic.main.match_schedule_detail_activity.*
import org.jetbrains.anko.design.snackbar

class MatchScheduleDetailActivity : AppCompatActivity(), MatchScheduleDetailContract.View {

    private val matchScheduleDetailPresenter: MatchScheduleDetailPresenter = MatchScheduleDetailPresenter()
    private var menuItem: MenuItem? = null
    private var isFavoriteMatch: Boolean = false
    private var event: Event? = null
    private var favoriteEvent: FavoriteMatch? = null

    companion object {
        const val arg_match_bundle_key = "match"
        const val arg_favorite_match_bundle_key = "favorite_match"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.match_schedule_detail_activity)
        event = intent.getParcelableExtra(arg_match_bundle_key)
        favoriteEvent = intent.getParcelableExtra(arg_favorite_match_bundle_key)
        matchScheduleDetailPresenter.attach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        matchScheduleDetailPresenter.detach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_activity_options_menu, menu)
        menuItem = menu?.findItem(R.id.add_to_favorite)
        //sometimes menuitem is still null, so process presenter after menuitem rendered successfully
        matchScheduleDetailPresenter.setupView(event, favoriteEvent)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                true
            }
            R.id.add_to_favorite -> {
                addToFavorite()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

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

    override fun onSaveFavoriteMatchLoading() {
        showDetailActivityActionLoading()
    }

    override fun onSaveFavoriteMatchFailed() {
        snackbar(sv_detail_root_view, getString(R.string.str_save_favorite_failed))
        hideDetailActivityActionLoading()
    }

    override fun onSaveFavoriteMatchSuccess() {
        snackbar(sv_detail_root_view, getString(R.string.str_save_favorite_success))
    }

    override fun onUnFavoriteMatchSuccess() {
        snackbar(sv_detail_root_view, getString(R.string.str_delete_favorite_success))
    }

    override fun onSetFavoriteView(isFavorite: Boolean) {
        hideDetailActivityActionLoading()
        isFavoriteMatch = isFavorite
        if (isFavorite) {
            menuItem?.icon = ContextCompat.getDrawable(this, ic_favorited)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
        }
    }

    override fun onUnFavoriteMatchLoading() {
        showDetailActivityActionLoading()
    }

    override fun onUnFavoriteMatchFailed() {
        snackbar(sv_detail_root_view, getString(R.string.str_delete_favorite_failed))
        hideDetailActivityActionLoading()
    }

    override fun onSetupViewFailed(isRetryAble: Boolean) {
        hideDetailActivityActionLoading()

        if(isRetryAble) {
            snackbar(sv_detail_root_view, getString(R.string.str_generic_error_failed),
                getString(R.string.str_retry)) {matchScheduleDetailPresenter.setupView(event!!, favoriteEvent!!)}
        } else {
            snackbar(sv_detail_root_view, getString(R.string.str_generic_error_failed),
                getString(R.string.str_back)) {finish()}
        }
    }

    override fun onSetupViewSuccessed(eventLoaded: Event) {
        event = eventLoaded
        hideDetailActivityActionLoading()
        initView(event)
        supportActionBar?.title = eventLoaded.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        matchScheduleDetailPresenter.isFavoriteMatch(eventLoaded.id, this)
        matchScheduleDetailPresenter.getHomeTeamBadge(eventLoaded.homeTeamId)
        matchScheduleDetailPresenter.getAwayTeamBadge(eventLoaded.awayTeamId)
    }

    override fun onGetEventDetailsByIdLoading() {
        showDetailActivityActionLoading()
    }

    override fun onGetEventDetailsByIdFailed() {
        hideDetailActivityActionLoading()
    }

    private fun initView(event: Event?){
        showMatchSummaryView(event)
        showMatchGoalsView(event)
        showMatchShotsView(event)
        showLineupsGoalKeeperView(event)
        showLineupsDefenseView(event)
        showLineupsMidfieldView(event)
        showLineupsForwardView(event)
        showLineupsSubtitutesView(event)
    }

    override fun showDetailActivityActionLoading(){
        pb_loading_detail_activity_action.show()
    }

    override fun hideDetailActivityActionLoading(){
        pb_loading_detail_activity_action.hide()
    }

    private fun showMatchSummaryView(event: Event?) {
        if(event?.date != null) {
            tv_match_date.text = event.date.adjustTimePattern(Utils.originEventDateTimeFormat,
                Utils.matchEventDateTimeFormat)
        }
        tv_home_team_name.text = event?.homeTeamName
        tv_home_team_score.text = event?.homeScoreNumber
        tv_away_team_name.text = event?.awayTeamName
        tv_away_team_score.text = event?.awayScoreNumber
    }

    private fun showMatchGoalsView(event: Event?) {
        tv_home_team_goals_summary.text = event?.homeGoalDetails?.splitted()
        tv_away_team_goals_summary.text= event?.awayGoalDetails?.splitted()

    }

    private fun showMatchShotsView(event: Event?) {
        tv_home_team_shot_number.text = event?.homeTeamShotsNumber
        tv_away_team_shot_number.text = event?.awayTeamShotsNumber
    }

    private fun showLineupsGoalKeeperView(event: Event?) {
        tv_home_team_goalkeepers.text = event?.homeLineupGoalkeeper?.splitted()
        tv_away_team_goalkeepers.text = event?.awayLineupGoalkeeper?.splitted()
    }

    private fun showLineupsDefenseView(event: Event?){
        tv_home_team_defenses.text = event?.homeLineupDefense?.splitted()
        tv_away_team_defenses.text = event?.awayLineupDefense?.splitted()
    }

    private fun showLineupsMidfieldView(event: Event?){
        tv_home_team_midfields.text = event?.homeLineupMidfield?.splitted()
        tv_away_team_midfields.text = event?.awayLineupMidfield?.splitted()
    }

    private fun showLineupsForwardView(event: Event?){
        tv_home_team_forwards.text = event?.homeLineupForward?.splitted()
        tv_away_team_forwards.text = event?.awayLineupForward?.splitted()
    }

    private fun showLineupsSubtitutesView(event: Event?){
        tv_home_team_subtitutes.text = event?.homeLineupSubstitutes?.splitted()
        tv_away_team_subtitutes.text = event?.awayLineupSubstitutes?.splitted()
    }

    private fun addToFavorite(){
        if(event == null) {
            return
        }
        if(!isFavoriteMatch) {
            matchScheduleDetailPresenter.saveFavoriteMatch(event!!, this)
        } else {
            matchScheduleDetailPresenter.unFavoriteMatch(event!!.id, this)
        }
    }
}