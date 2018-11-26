package com.muslimlife.tf.footballappkotlang.features.team.detail.overview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.Team
import kotlinx.android.synthetic.main.team_overview_fragment.*

class TeamOverviewFragment : Fragment() {
    companion object {
        private const val arg_team_data = "team_data"

        fun newInstance(teamData: Team): TeamOverviewFragment {
            val args = Bundle()
            args.putParcelable(arg_team_data, teamData)
            val fragment = TeamOverviewFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.team_overview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val teamData = arguments?.getParcelable<Team>(arg_team_data)
        tv_team_overview_description.text = teamData?.descriptionInEnglish
    }
}