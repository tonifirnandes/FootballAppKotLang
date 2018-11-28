package com.muslimlife.tf.footballappkotlang.features.team.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.*
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
    override fun showSearchTeamsLoading() {
        showGetTeamsLoading()
    }

    override fun onFoundTeams(newFoundTeams: List<Team>) {
        hideGetTeamsLoading()
        updateFoundTeams(newFoundTeams)
    }

    override fun onNotFoundTeams() {
        hideGetTeamsLoading()
    }

    private lateinit var selectedLeagueId: String
    private lateinit var selectedLeagueName: String
    private var searchActionView: SearchView? = null
    private var supportedActionBar: ActionBar? = null
    private var foundTeams: MutableList<Team> = mutableListOf()
    private lateinit var searchAdapater: TeamsAdapter

    private val teamsPresenter: TeamsPresenter = TeamsPresenter(SchedulerProvider())

    companion object {
        fun newInstance(): TeamsFragment = TeamsFragment()
    }

    fun forceHavingControlToActionBar(supportActionBar: ActionBar?) {
        supportedActionBar = supportActionBar
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.base_rv_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTeamListView()
        setHasOptionsMenu(true)
        teamsPresenter.attach(this)
        setupLeagueSpinner()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search_menu, menu)
        setupSearchView(menu?.findItem(R.id.actionSearch)?.actionView as SearchView?, menu?.findItem(R.id.actionSearch))
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

    private fun initTeamListView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_search_teams.layoutManager = layoutManager
        searchAdapater = TeamsAdapter(foundTeams, context)
        rv_search_teams.adapter = searchAdapater
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

    private fun closeSearchView() {
        if (searchActionView?.isIconified == false) {
            searchActionView?.isIconified = true
        }
        clearFoundTeams()
    }

    private fun showMainTeams() {
        supportedActionBar?.setDisplayHomeAsUpEnabled(false)
        closeSearchView()
        rl_main_teams.show()
        rl_search_teams.hide()
    }

    private fun showSearchTeams() {
        initTeamListView()
        supportedActionBar?.setDisplayHomeAsUpEnabled(true)
        rl_main_teams.hide()
        rl_search_teams.show()
    }

    private fun updateFoundTeams(newFoundTeams: List<Team>) {
        synchronized(foundTeams) {
            clearFoundTeams()
            foundTeams.addAll(newFoundTeams)
            searchAdapater.notifyDataSetChanged()
        }
    }

    private fun clearFoundTeams() {
        foundTeams.clear()
    }

    private fun searchTeams(teamName: String) {
        if (TextUtils.isEmpty(teamName)) return
        teamsPresenter.findTeams(teamName)
    }

    private fun setupSearchView(searchView: SearchView?, searchMenu: MenuItem?) {
        searchActionView = searchView
        searchView?.queryHint = getString(R.string.str_search_teams_hint)

        searchView?.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchTeams(query)
                return true

            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchTeams(newText)
                return true
            }
        })

        searchMenu?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                showSearchTeams()
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                showMainTeams()
                return true
            }
        })
    }

}