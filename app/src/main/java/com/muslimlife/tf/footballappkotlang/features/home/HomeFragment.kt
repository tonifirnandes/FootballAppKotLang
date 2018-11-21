package com.muslimlife.tf.footballappkotlang.features.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.League
import com.muslimlife.tf.footballappkotlang.extensions.hide
import com.muslimlife.tf.footballappkotlang.extensions.show
import kotlinx.android.synthetic.main.home_fragment.*
import org.jetbrains.anko.design.snackbar

class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var selectedLeagueId: String
    private lateinit var selectedLeagueName: String

    private val homePresenter: HomePresenter = HomePresenter()

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstInitHomeView()
        homePresenter.attach(this)
        homePresenter.getAllLeagues()
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.detach()
    }

    override fun showGetAllLeaguesLoading() {
        pb_home.show()
    }

    override fun onGetAllLeaguesFailed() {
        pb_home.hide()
        snackbar(
            home_view_root, getString(R.string.str_generic_error_failed),
            getString(R.string.str_retry)
        ) { homePresenter.getAllLeagues() }
    }

    override fun onGetAllLeaguesSuccess(soccerLeagues: List<League>) {
        pb_home.hide()
        selectedLeagueId = soccerLeagues[0].id
        selectedLeagueName = soccerLeagues[0].name
        if (fragmentManager == null) {
            snackbar(
                home_view_root, getString(R.string.str_generic_error_failed),
                getString(R.string.str_retry)
            ) { onGetAllLeaguesSuccess(soccerLeagues) }
            return
        }
        viewpager_main.adapter = FootBallMatchScheduleAdapter(
            fragmentManager, selectedLeagueId,
            resources.getStringArray(R.array.schedule_types)
        )
        tabs_main.setupWithViewPager(viewpager_main)
        viewpager_main.show()
        tabs_main.show()
    }

    private fun firstInitHomeView() {
        //first loading
        viewpager_main.hide()
        tabs_main.hide()
    }

}