package com.muslimlife.tf.footballappkotlang.features.team.detail.player.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.Player
import com.muslimlife.tf.footballappkotlang.extensions.hide
import com.muslimlife.tf.footballappkotlang.extensions.rx.SchedulerProvider
import com.muslimlife.tf.footballappkotlang.extensions.show
import kotlinx.android.synthetic.main.base_rv_fragment.*

class TeamPlayerFragment : Fragment(), TeamPlayerContract.View {

    private val teamPlayersPresenter: TeamPlayerPresenter = TeamPlayerPresenter(SchedulerProvider())
    private var teamId: String? = null

    companion object {
        private const val arg_team_id = "team_id"

        fun newInstance(teamId: String): TeamPlayerFragment {
            val args = Bundle()
            args.putString(arg_team_id, teamId)
            val fragment = TeamPlayerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.base_rv_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamId = arguments?.getString(arg_team_id)
        teamPlayersPresenter.attach(this)
    }

    override fun onResume() {
        super.onResume()
        teamPlayersPresenter.getTeamPlayersByTeamId(teamId ?: "")
    }

    override fun onDestroy() {
        super.onDestroy()
        teamPlayersPresenter.detach()
    }

    override fun showGetTeamPlayersLoading() {
        base_pb_rv_faragment.show()
    }

    override fun onGetTeamPlayersSuccess(playerList: List<Player>) {
        hideGetPlayersLoading()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        base_rv_list.layoutManager = layoutManager
        base_rv_list.adapter =
                TeamPlayerRecyclerViewAdapter(playerList, context)
    }

    override fun onGetTeamPlayersFailed() {
        hideGetPlayersLoading()
    }

    private fun hideGetPlayersLoading() {
        base_pb_rv_faragment.hide()
    }
}