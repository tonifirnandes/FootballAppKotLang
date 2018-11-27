package com.muslimlife.tf.footballappkotlang.features.home.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.Event
import com.muslimlife.tf.footballappkotlang.extensions.hide
import com.muslimlife.tf.footballappkotlang.extensions.show
import kotlinx.android.synthetic.main.football_match_schedule_fragment.*

class FootBallMatchScheduleFragment : Fragment(), FootBallMatchesScheduleContract.View {

    private var scheduleType: Int = -1
    private val matchesSchedulePresenter: FootBallMatchesSchedulePresenter = FootBallMatchesSchedulePresenter()

    companion object {
        const val previous_schedule: Int = 0
        const val next_schedule: Int = 1
        private const val arg_schedule_type = "schedule_type"
        private const val arg_league_id = "league_id"
        fun newInstance(scheduleType: Int, leagueId: String): FootBallMatchScheduleFragment {
            val args = Bundle()
            args.putInt(arg_schedule_type, scheduleType)
            args.putString(arg_league_id, leagueId)
            val fragment = FootBallMatchScheduleFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.football_match_schedule_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchesSchedulePresenter.attach(this)
        scheduleType = arguments?.getInt(arg_schedule_type) ?: -1
        matchesSchedulePresenter.getMatchesScheduleByType(
            scheduleType,
            arguments?.get(arg_league_id).toString()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        matchesSchedulePresenter.detach()
    }

    override fun showGetMatchesScheduleLoading() {
        pb_loading_match_schedule.show()
    }

    override fun onGetMatchesScheduleSuccess(matchScheduleList: List<Event>) {
        hideGetMatchesScheduleLoading()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_match_schedule.layoutManager = layoutManager
        rv_match_schedule.adapter =
                MatchScheduleAdapter(matchScheduleList, context, scheduleType)
    }

    override fun onGetMatchesScheduleFailed() {
        hideGetMatchesScheduleLoading()
    }

    private fun hideGetMatchesScheduleLoading() {
        pb_loading_match_schedule.hide()
    }

}