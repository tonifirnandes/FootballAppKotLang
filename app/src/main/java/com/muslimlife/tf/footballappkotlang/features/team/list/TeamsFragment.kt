package com.muslimlife.tf.footballappkotlang.features.team.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.Team
import com.muslimlife.tf.footballappkotlang.extensions.hide
import com.muslimlife.tf.footballappkotlang.extensions.rx.SchedulerProvider
import com.muslimlife.tf.footballappkotlang.extensions.show
import kotlinx.android.synthetic.main.base_rv_fragment.*

class TeamsFragment : Fragment(), TeamsContract.View {

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
    }

    override fun onResume() {
        super.onResume()
        //refactor get leagues
        teamsPresenter.getTeamsByLeagueId("4328")
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

}