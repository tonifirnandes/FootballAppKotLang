package com.muslimlife.tf.footballappkotlang.features.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.muslimlife.tf.footballappkotlang.features.favorite.FavoritesMatchFragment
import com.muslimlife.tf.footballappkotlang.features.home.list.FootBallMatchScheduleFragment

class FootBallMatchScheduleAdapter(fm: FragmentManager,
                                   private val leagueId: String,
                                   private val scheduleTypes: Array<String>) : FragmentStatePagerAdapter(fm) {


    override fun getCount(): Int {
        //static we only offer previous and next 15 matches
        return scheduleTypes.size
    }

    override fun getItem(p0: Int): Fragment {
        return when(p0) {
            0 -> FootBallMatchScheduleFragment.newInstance(FootBallMatchScheduleFragment.previous_schedule, leagueId)
            1 -> FootBallMatchScheduleFragment.newInstance(FootBallMatchScheduleFragment.next_schedule, leagueId)
            2 -> FavoritesMatchFragment.newInstance()
            else -> Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> scheduleTypes[0]
            1 -> scheduleTypes[1]
            2 -> scheduleTypes[2]
            else -> ""
        }
    }
}