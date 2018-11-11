package com.muslimlife.tf.footballappkotlang.features.detail

interface MatchScheduleDetailContract {
    interface View{
        fun onGetHomeTeamBadgeSuccessed(url: String)
        fun onGetAwayTeamBadgeFailed()
        fun onGetAwayTeamBadgeSuccessed(url: String)
        fun onGetHomeTeamBadgeFailed()
    }

    interface Presenter {
        fun getHomeTeamBadge(id: String)
        fun getAwayTeamBadge(id: String)
    }
}