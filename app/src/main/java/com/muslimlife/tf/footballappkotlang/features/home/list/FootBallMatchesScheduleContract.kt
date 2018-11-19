package com.muslimlife.tf.footballappkotlang.features.home.list

import com.muslimlife.tf.footballappkotlang.data.model.Event
import com.muslimlife.tf.footballappkotlang.generics.BaseMvpPresenter
import com.muslimlife.tf.footballappkotlang.generics.BaseView

interface FootBallMatchesScheduleContract {
    interface View : BaseView {
        fun showGetMatchesScheduleLoading()
        fun onGetMatchesScheduleSuccessed(matchScheduleList: List<Event>)
        fun onGetMatchesScheduleFailed()
    }

    interface Presenter : BaseMvpPresenter<FootBallMatchesScheduleContract.View> {
        fun getMatchesScheduleByType(
            scheduleType: Int?,
            leagueId: String
        )
    }
}