package com.muslimlife.tf.footballappkotlang.features.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.League
import com.muslimlife.tf.footballappkotlang.data.preference.SharedPrefs
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
        selectedLeagueId = SharedPrefs.selectedLeagueId ?: soccerLeagues[0].id
        selectedLeagueName = SharedPrefs.selectedLeagueName ?: soccerLeagues[0].name
        SharedPrefs.selectedLeagueId = selectedLeagueId
        SharedPrefs.selectedLeagueName = selectedLeagueName
        if (fragmentManager == null || context == null) {
            snackbar(
                home_view_root, getString(R.string.str_generic_error_failed),
                getString(R.string.str_retry)
            ) { onGetAllLeaguesSuccess(soccerLeagues) }
            return
        }
        initMatchesView()
        setupLeagueSpinner(soccerLeagues)
    }

    private fun initMatchesView() {
        viewpager_main.adapter = FootBallMatchScheduleAdapter(
            fragmentManager, selectedLeagueId,
            resources.getStringArray(R.array.schedule_types)
        )
        tabs_main.setupWithViewPager(viewpager_main)
        viewpager_main.show()
        tabs_main.show()
    }

    private fun setupLeagueSpinner(soccerLeagues: List<League>) {
        val thisContext = context
        if (thisContext == null) {
            snackbar(
                home_view_root, getString(R.string.str_generic_error_failed),
                getString(R.string.str_retry)
            ) { onGetAllLeaguesSuccess(soccerLeagues) }
            return
        }


        val spinnerItems = soccerLeagues.map { it -> it.name }
        val spinnerAdapter = ArrayAdapter(thisContext, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        sp_leagues.adapter = spinnerAdapter
        sp_leagues.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedLeagueId = soccerLeagues[position].id
                selectedLeagueName = soccerLeagues[position].name
                SharedPrefs.selectedLeagueId = selectedLeagueId
                SharedPrefs.selectedLeagueName = selectedLeagueName
                initMatchesView()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        sp_leagues.setSelection(spinnerItems.indexOf(SharedPrefs.selectedLeagueName))
        sp_leagues.show()
    }

}