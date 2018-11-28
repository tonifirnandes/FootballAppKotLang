package com.muslimlife.tf.footballappkotlang.features.home

import com.muslimlife.tf.footballappkotlang.data.model.Event
import com.muslimlife.tf.footballappkotlang.data.model.League
import com.muslimlife.tf.footballappkotlang.generics.BaseMvpPresenter
import com.muslimlife.tf.footballappkotlang.generics.BaseView

interface HomeContract {
    //linked to activity/fragment lifecycle
    interface View : BaseView {
        fun showGetAllLeaguesLoading()
        fun onGetAllLeaguesFailed()
        fun onGetAllLeaguesSuccess(soccerLeagues: List<League>)
        fun showFindMatchesLoading()
        fun onNotFoundMatches()
        fun onFoundMathces(found: List<Event>)
    }

    interface Presenter : BaseMvpPresenter<HomeContract.View> {
        fun getAllLeagues()
        fun findMatches(eventSearched: String)
    }
}