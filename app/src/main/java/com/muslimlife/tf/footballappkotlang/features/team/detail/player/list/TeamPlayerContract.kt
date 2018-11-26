package com.muslimlife.tf.footballappkotlang.features.team.detail.player.list

import com.muslimlife.tf.footballappkotlang.data.model.Player
import com.muslimlife.tf.footballappkotlang.generics.BaseMvpPresenter
import com.muslimlife.tf.footballappkotlang.generics.BaseView

interface TeamPlayerContract {
    interface View : BaseView {
        fun showGetTeamPlayersLoading()
        fun onGetTeamPlayersSuccess(playerList: List<Player>)
        fun onGetTeamPlayersFailed()
    }

    interface Presenter : BaseMvpPresenter<TeamPlayerContract.View> {
        fun getTeamPlayersByTeamId(teamId: String)
    }
}