package com.muslimlife.tf.footballappkotlang.features.team.detail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.muslimlife.tf.footballappkotlang.data.model.Team
import com.muslimlife.tf.footballappkotlang.features.team.detail.overview.TeamOverviewFragment
import com.muslimlife.tf.footballappkotlang.features.team.detail.player.list.TeamPlayerFragment

class TeamDetailInfoTabAdapter(
    fm: FragmentManager?,
    private val infoTypes: Array<String>,
    private val teamData: Team
) : FragmentStatePagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> TeamOverviewFragment.newInstance(teamData)
            1 -> TeamPlayerFragment.newInstance(teamData.id)
            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return infoTypes.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> infoTypes[0]
            1 -> infoTypes[1]
            else -> ""
        }
    }

}