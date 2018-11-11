package com.muslimlife.tf.footballappkotlang.features.home.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRest
import com.muslimlife.tf.footballappkotlang.data.api.FootBallRestService
import com.muslimlife.tf.footballappkotlang.data.model.Event
import com.rahmat.app.footballclub.extensions.hide
import com.rahmat.app.footballclub.extensions.show
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.football_match_schedule_fragment.*
import java.lang.UnsupportedOperationException

class FootBallMatchScheduleFragment : Fragment() {

    private lateinit var compositeDisposable: CompositeDisposable

    private lateinit var service: FootBallRest

    companion object {
        const val previous_schedule = 0
        const val next_schedule = 1
        private const val arg_schedule_type = "schedule_type"
        private const val arg_league_id = "league_id"
        fun newInstance(scheduleType: Int, leagueId: String) : FootBallMatchScheduleFragment {
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
        pb_loading_match_schedule.show()
        compositeDisposable = CompositeDisposable()
        service = FootBallRestService.instance()
        when(arguments?.get(arg_schedule_type)) {
            0 -> getLastFifteenSoccerMatchByLeagueId(arguments?.get(arg_league_id).toString())
            1 -> getNextFifteenSoccerMatchByLeagueId(arguments?.get(arg_league_id).toString())
            else -> throw UnsupportedOperationException()
        }
    }

    fun getLastFifteenSoccerMatchByLeagueId(id: String) {
        compositeDisposable.add(service.getLastmatch(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                getMatchScheduleByTypeSuccess(it.events)
            }, {
                getMatchScheduleByTypeFailed()
            }))
    }

    fun getNextFifteenSoccerMatchByLeagueId(id: String) {
        compositeDisposable.add(service.getUpcomingMatch(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                getMatchScheduleByTypeSuccess(it.events)
            }, {
                getMatchScheduleByTypeFailed()
            }))
    }

    fun getMatchScheduleByTypeSuccess(matchScheduleList: List<Event>) {
        pb_loading_match_schedule.hide()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_match_schedule.layoutManager = layoutManager
        rv_match_schedule.adapter =
                MatchScheduleAdapter(matchScheduleList, context)
    }

    fun getMatchScheduleByTypeFailed() {
        pb_loading_match_schedule.hide()
    }

}