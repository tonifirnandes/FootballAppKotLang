package com.muslimlife.tf.footballappkotlang.features.home

import com.muslimlife.tf.footballappkotlang.data.model.League

interface HomeContract {
    //linked to activity/fragment lifecycle
    interface View {
        fun showGetAllLeaguesLoading()
        fun onGetAllLeaguesFailed()
        fun onGetAllLeaguesSuccessed(soccerLeagues: List<League>)
    }

    interface Presenter {
        fun getAllLeagues()
    }
}