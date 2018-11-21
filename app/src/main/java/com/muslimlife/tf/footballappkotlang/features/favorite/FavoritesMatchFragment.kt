package com.muslimlife.tf.footballappkotlang.features.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.FavoriteMatch
import com.muslimlife.tf.footballappkotlang.extensions.hide
import com.muslimlife.tf.footballappkotlang.extensions.show
import kotlinx.android.synthetic.main.football_match_schedule_fragment.*

class FavoritesMatchFragment : Fragment(), FavoritesMatchContract.View {

    private val matchesSchedulePresenter: FavoritesMatchPresenter = FavoritesMatchPresenter()

    companion object {
        fun newInstance(): FavoritesMatchFragment = FavoritesMatchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.football_match_schedule_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchesSchedulePresenter.attach(this)
    }

    override fun onResume() {
        super.onResume()
        matchesSchedulePresenter.getSavedFavoritesMatchLocally(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        matchesSchedulePresenter.detach()
    }

    override fun showGetFavoritesMatchLoading() {
        pb_loading_match_schedule.show()
    }

    override fun onGetFavoritesMatchSuccess(favoriteMatchList: List<FavoriteMatch>) {
        hideGetMatchesScheduleLoading()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_match_schedule.layoutManager = layoutManager
        rv_match_schedule.adapter =
                FavoritesMatchAdapter(favoriteMatchList, context)
    }

    override fun onGetFavoritesMatchFailed() {
        hideGetMatchesScheduleLoading()
    }

    private fun hideGetMatchesScheduleLoading() {
        pb_loading_match_schedule.hide()
    }

}