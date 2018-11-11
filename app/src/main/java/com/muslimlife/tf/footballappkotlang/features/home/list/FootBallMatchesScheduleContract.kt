package com.muslimlife.tf.footballappkotlang.features.home.list

import com.muslimlife.tf.footballappkotlang.data.model.Event

interface FootBallMatchesScheduleContract {
    interface View{
        fun showGetMatchesScheduleLoading()
        fun onGetMatchesScheduleSuccessed(matchScheduleList: List<Event>)
        fun onGetMatchesScheduleFailed()
    }

    interface Presenter{
        fun getMatchesScheduleByType(scheduleType: Int?, leagueId: String)
    }
}