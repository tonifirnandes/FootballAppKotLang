package com.muslimlife.tf.footballappkotlang.features.home

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
import com.muslimlife.tf.footballappkotlang.data.model.Event
import com.muslimlife.tf.footballappkotlang.data.model.League
import com.muslimlife.tf.footballappkotlang.data.preference.SharedPrefs
import com.muslimlife.tf.footballappkotlang.extensions.hide
import com.muslimlife.tf.footballappkotlang.extensions.show
import com.muslimlife.tf.footballappkotlang.features.home.list.MatchScheduleAdapter
import kotlinx.android.synthetic.main.home_fragment.*
import org.jetbrains.anko.design.snackbar

class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var selectedLeagueId: String
    private lateinit var selectedLeagueName: String
    private var searchActionView: SearchView? = null
    private var supportedActionBar: ActionBar? = null
    private var foundMatches: MutableList<Event> = mutableListOf()
    private lateinit var searchAdapter: MatchScheduleAdapter

    private val homePresenter: HomePresenter = HomePresenter()

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    fun forceHavingControlToActionBar(supportActionBar: ActionBar?) {
        supportedActionBar = supportActionBar
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        homePresenter.attach(this)
        homePresenter.getAllLeagues()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search_menu, menu)
        setupSearchView(menu?.findItem(R.id.actionSearch)?.actionView as SearchView?, menu?.findItem(R.id.actionSearch))
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.detach()
    }

    override fun showGetAllLeaguesLoading() {
        showActionLoading()
    }

    override fun onGetAllLeaguesFailed() {
        hideActionLoading()
        snackbar(
            home_view_root, getString(R.string.str_generic_error_failed),
            getString(R.string.str_retry)
        ) { homePresenter.getAllLeagues() }
    }

    override fun onGetAllLeaguesSuccess(soccerLeagues: List<League>) {
        hideActionLoading()
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
        setupLeagueSpinner(soccerLeagues)
        initMatchesView()
    }

    override fun showFindMatchesLoading() {
        showActionLoading()
    }

    override fun onNotFoundMatches() {
        hideActionLoading()
    }

    override fun onFoundMathces(found: List<Event>) {
        hideActionLoading()
        updateFoundMatches(found)
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
        sp_leagues.setSelection(spinnerItems.indexOf(SharedPrefs.selectedLeagueName), true)
        sp_leagues.show()
    }

    private fun setupSearchView(searchView: SearchView?, searchMenu: MenuItem?) {
        searchActionView = searchView
        searchView?.queryHint = getString(R.string.str_search_matches_hint)

        searchView?.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchMatch(query)
                return true

            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchMatch(newText)
                return true
            }
        })

        searchMenu?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                showSearchMatches()
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                showMainMatches()
                return true
            }
        })
    }

    private fun showActionLoading() {
        pb_home.show()
    }

    private fun hideActionLoading() {
        pb_home.hide()
    }


    private fun closeSearchView() {
        if (searchActionView?.isIconified == false) {
            searchActionView?.isIconified = true
        }
        clearFoundMathces()
    }

    private fun showMainMatches() {
        supportedActionBar?.setDisplayHomeAsUpEnabled(false)
        closeSearchView()
        rl_main_matches.show()
        rl_search_matches.hide()
    }

    private fun showSearchMatches() {
        initSearchViewList()
        supportedActionBar?.setDisplayHomeAsUpEnabled(true)
        rl_main_matches.hide()
        rl_search_matches.show()
    }

    private fun updateFoundMatches(newFoundMatches: List<Event>) {
        synchronized(foundMatches) {
            clearFoundMathces()
            foundMatches.addAll(newFoundMatches)
            searchAdapter.notifyDataSetChanged()
        }
    }

    private fun clearFoundMathces() {
        foundMatches.clear()
    }

    private fun searchMatch(eventName: String) {
        if (TextUtils.isEmpty(eventName)) return
        homePresenter.findMatches(eventName)
    }

    private fun initSearchViewList() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_search_match_list.layoutManager = layoutManager
        searchAdapter = MatchScheduleAdapter(foundMatches, context, -1)
        rv_search_match_list.adapter = searchAdapter
    }

}