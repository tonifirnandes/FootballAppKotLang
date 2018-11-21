package com.muslimlife.tf.footballappkotlang.features.favorite

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.FavoriteMatch
import com.muslimlife.tf.footballappkotlang.extensions.GenericDateFormatConstant
import com.muslimlife.tf.footballappkotlang.extensions.adjustTimePattern
import com.muslimlife.tf.footballappkotlang.features.detail.MatchScheduleDetailActivity
import kotlinx.android.synthetic.main.football_match_schedule_item.view.*
import org.jetbrains.anko.startActivity

class FavoritesMatchAdapter(private val scheduleList: List<FavoriteMatch>, private val context: Context?) :
    RecyclerView.Adapter<FavoritesMatchAdapter.ClubViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        return ClubViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.football_match_schedule_item,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = scheduleList.size


    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        holder.bind(scheduleList[position])
    }

    inner class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(event: FavoriteMatch) {
            itemView.tv_schedule_date.text = event.matchDate.adjustTimePattern(
                GenericDateFormatConstant.originEventDateTimeFormat,
                GenericDateFormatConstant.matchEventDateTimeFormat
            )
            itemView.tv_hometeam_name.text = event.homeTeamName
            itemView.tv_hometeam_score.text = event.homeTeamScore
            itemView.tv_awayteam_name.text = event.awayTeamName
            itemView.tv_awayteam_score.text = event.awayTeamScore

            itemView.setOnClickListener {
                itemView.context.startActivity<MatchScheduleDetailActivity>(
                    MatchScheduleDetailActivity.arg_favorite_match_bundle_key to event
                )
            }
        }
    }

}