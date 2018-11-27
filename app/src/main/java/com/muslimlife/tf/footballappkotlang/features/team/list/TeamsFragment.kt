package com.muslimlife.tf.footballappkotlang.features.team.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestConstant
import com.muslimlife.tf.footballappkotlang.data.model.Team
import com.muslimlife.tf.footballappkotlang.data.preference.SharedPrefs
import com.muslimlife.tf.footballappkotlang.extensions.hide
import com.muslimlife.tf.footballappkotlang.extensions.rx.SchedulerProvider
import com.muslimlife.tf.footballappkotlang.extensions.show
import kotlinx.android.synthetic.main.base_rv_fragment.*

class TeamsFragment : Fragment(), TeamsContract.View {

    private lateinit var selectedLeagueId: String
    private lateinit var selectedLeagueName: String

    private val teamsPresenter: TeamsPresenter = TeamsPresenter(SchedulerProvider())

    companion object {
        fun newInstance(): TeamsFragment = TeamsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.base_rv_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamsPresenter.attach(this)
        setupLeagueSpinner()
    }

    override fun onResume() {
        super.onResume()
        getTeamListByLeagueId()
    }

    override fun onDestroy() {
        super.onDestroy()
        teamsPresenter.detach()
    }

    override fun showGetTeamsLoading() {
        base_pb_rv_faragment.show()
    }

    override fun onGetTeamsSuccess(teamList: List<Team>) {
        hideGetTeamsLoading()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        base_rv_list.layoutManager = layoutManager
        base_rv_list.adapter =
                TeamsAdapter(teamList, context)
    }

    override fun onGetTeamsFailed() {
        hideGetTeamsLoading()
    }

    private fun hideGetTeamsLoading() {
        base_pb_rv_faragment.hide()
    }

    private fun setupLeagueSpinner() {
        val thisContext = context
        if (thisContext == null || SharedPrefs.leaguesData == null || SharedPrefs.leaguesData?.leagues == null) {
            return
        }
        val soccerLeagues = SharedPrefs.leaguesData?.leagues
        val spinnerItems = soccerLeagues?.map { league ->
            league.name
        }

        if (spinnerItems != null) {
            val spinnerAdapter = ArrayAdapter(thisContext, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
            sp_leagues.adapter = spinnerAdapter
            sp_leagues.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selectedLeagueId = soccerLeagues[position].id
                    selectedLeagueName = soccerLeagues[position].name
                    SharedPrefs.selectedLeagueId = selectedLeagueId
                    SharedPrefs.selectedLeagueName = selectedLeagueName
                    getTeamListByLeagueId()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
            sp_leagues.setSelection(spinnerItems.indexOf(SharedPrefs.selectedLeagueName))
            sp_leagues.show()
        }

    }

    private fun getTeamListByLeagueId() {
        teamsPresenter.getTeamsByLeagueId(SharedPrefs.selectedLeagueId ?: FootBallRestConstant.defaultSelectedLeagueId)
    }


}