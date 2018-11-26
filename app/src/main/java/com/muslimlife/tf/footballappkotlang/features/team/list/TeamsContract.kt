package com.muslimlife.tf.footballappkotlang.features.team.list

import com.muslimlife.tf.footballappkotlang.data.model.Team
import com.muslimlife.tf.footballappkotlang.generics.BaseMvpPresenter
import com.muslimlife.tf.footballappkotlang.generics.BaseView

interface TeamsContract {
    interface View : BaseView {
        fun showGetTeamsLoading()
        fun onGetTeamsSuccess(teamList: List<Team>)
        fun onGetTeamsFailed()
    }

    interface Presenter : BaseMvpPresenter<TeamsContract.View> {
        fun getTeamsByLeagueId(leagueId: String)
    }
}