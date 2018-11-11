package com.muslimlife.tf.footballappkotlang.features.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRest
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestConstant
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import com.muslimlife.tf.footballappkotlang.data.model.League
import com.rahmat.app.footballclub.extensions.hide
import com.rahmat.app.footballclub.extensions.show
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_activity.*
import org.jetbrains.anko.design.snackbar

class HomeActivity : AppCompatActivity() {

    private lateinit var compositeDisposable: CompositeDisposable

    private lateinit var soccerLeagues: List<League>

    lateinit var selectedLeagueId: String

    private lateinit var service: FootBallRest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
        service = FootBallRestService.instance()
        setContentView(R.layout.home_activity)
        //first loading
        viewpager_main.hide()
        tabs_main.hide()
        getAllLeagues()
    }

    fun showRetryView() {
        snackbar(home_view_root, getString(R.string.str_get_allleagues_failed),
            getString(R.string.str_retry)) {getAllLeagues()}
    }

    fun onLoadLeaguesFinished(leagues: List<League>) {
        pb_home.hide()
        soccerLeagues = leagues.filter { league -> league.category == FootBallRestConstant.apiSoccerCategory }
        selectedLeagueId = soccerLeagues[0].id
        btn_selected_league.text = soccerLeagues[0].name
        viewpager_main.adapter = FootBallMatchScheduleAdapter(supportFragmentManager, selectedLeagueId,
            resources.getStringArray(R.array.schedule_types))
        tabs_main.setupWithViewPager(viewpager_main)
        viewpager_main.show()
        tabs_main.show()
    }

    fun getAllLeagues(){
        pb_home.show()
        compositeDisposable.add(service.getAllLeagues()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                onLoadLeaguesFinished(it.leagues)
            }, {
                pb_home.hide()
                showRetryView()
            }))
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}