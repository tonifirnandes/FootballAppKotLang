package com.muslimlife.tf.footballappkotlang.features.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.League
import com.muslimlife.tf.footballappkotlang.extensions.hide
import com.muslimlife.tf.footballappkotlang.extensions.show
import kotlinx.android.synthetic.main.home_activity.*
import org.jetbrains.anko.design.snackbar

class HomeActivity : AppCompatActivity(), HomeContract.View {

    private lateinit var selectedLeagueId: String

    private lateinit var homePresenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        firstInitHomeView()
        homePresenter = HomePresenter()
        homePresenter.attach(this)
        homePresenter.getAllLeagues()
    }

    override fun showGetAllLeaguesLoading() {
        pb_home.show()
    }

    override fun onGetAllLeaguesFailed() {
        pb_home.hide()
        snackbar(home_view_root, getString(R.string.str_generic_error_failed),
            getString(R.string.str_retry)) {homePresenter.getAllLeagues()}
    }

    override fun onGetAllLeaguesSuccessed(soccerLeagues: List<League>) {
        pb_home.hide()
        selectedLeagueId = soccerLeagues[0].id
        btn_selected_league.text = soccerLeagues[0].name
        viewpager_main.adapter = FootBallMatchScheduleAdapter(supportFragmentManager, selectedLeagueId,
            resources.getStringArray(R.array.schedule_types))
        tabs_main.setupWithViewPager(viewpager_main)
        viewpager_main.show()
        tabs_main.show()
    }

    private fun firstInitHomeView(){
        //first loading
        viewpager_main.hide()
        tabs_main.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.detach()
    }
}