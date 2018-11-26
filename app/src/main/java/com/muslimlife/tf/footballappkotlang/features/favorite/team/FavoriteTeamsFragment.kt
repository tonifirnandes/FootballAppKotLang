package com.muslimlife.tf.footballappkotlang.features.favorite.team

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.local_db.FavoriteTeam
import com.muslimlife.tf.footballappkotlang.extensions.hide
import com.muslimlife.tf.footballappkotlang.extensions.show
import kotlinx.android.synthetic.main.base_rv_fragment.*

class FavoriteTeamsFragment : Fragment(), FavoriteTeamContract.View {

    private val favoriteTeamsPresenter = FavoriteTeamsPresenter()

    companion object {
        fun newInstance(): FavoriteTeamsFragment = FavoriteTeamsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.base_rv_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteTeamsPresenter.attach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteTeamsPresenter.detach()
    }

    override fun onResume() {
        super.onResume()
        favoriteTeamsPresenter.getSavedFavoritesMatchLocally(context)
    }

    override fun showGetFavoritesTeamLoading() {
        base_pb_rv_faragment.show()
    }

    override fun onGetFavoritesTeamSuccess(favoriteTeamList: List<FavoriteTeam>) {
        hideGetFavoriteTeamLoading()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        base_rv_list.layoutManager = layoutManager
        base_rv_list.adapter =
                FavoritesTeamAdapater(favoriteTeamList, context)
    }

    override fun onGetFavoritesTeamFailed() {
        hideGetFavoriteTeamLoading()
    }

    private fun hideGetFavoriteTeamLoading() {
        base_pb_rv_faragment.hide()
    }
}