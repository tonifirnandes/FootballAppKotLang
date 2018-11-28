package com.muslimlife.tf.footballappkotlang.features.home.list

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.Event
import com.muslimlife.tf.footballappkotlang.extensions.GenericDateFormatConstant
import com.muslimlife.tf.footballappkotlang.extensions.adjustTimePatternWithTimezone
import com.muslimlife.tf.footballappkotlang.extensions.hide
import com.muslimlife.tf.footballappkotlang.extensions.show
import com.muslimlife.tf.footballappkotlang.features.detail.MatchScheduleDetailActivity
import kotlinx.android.synthetic.main.football_match_schedule_item.view.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*


class MatchScheduleAdapter(
    private val scheduleList: List<Event>,
    private val context: Context?,
    private val scheduleType: Int
) :
    RecyclerView.Adapter<MatchScheduleAdapter.ClubViewHolder>() {

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
        fun bind(event: Event) {
            if (event.date != null && event.time != null) {
                val dateTime = event.date + " " + event.time
                itemView.tv_schedule_date.text = dateTime.adjustTimePatternWithTimezone(
                    GenericDateFormatConstant.originEventDateTimeFormatWithTimeZone,
                    GenericDateFormatConstant.matchEventDateTimeFormat,
                    GenericDateFormatConstant.timeZoneGMT7
                )
                itemView.tv_schedule_time.text = dateTime.adjustTimePatternWithTimezone(
                    GenericDateFormatConstant.originEventDateTimeFormatWithTimeZone,
                    GenericDateFormatConstant.matchEventTimeFormat,
                    GenericDateFormatConstant.timeZoneGMT7
                )
            }
            itemView.tv_hometeam_name.text = event.homeTeamName
            itemView.tv_hometeam_score.text = event.homeScoreNumber
            itemView.tv_awayteam_name.text = event.awayTeamName
            itemView.tv_awayteam_score.text = event.awayScoreNumber

            itemView.setOnClickListener {
                itemView.context.startActivity<MatchScheduleDetailActivity>(
                    MatchScheduleDetailActivity.arg_match_bundle_key to event
                )
            }

            if (scheduleType == FootBallMatchScheduleFragment.next_schedule) {
                itemView.iv_schedule_reminder.show()
                itemView.iv_schedule_reminder.setOnClickListener {
                    //handle google calender
                    addToGoogleCalender(event)
                }
            } else {
                itemView.iv_schedule_reminder.hide()
            }
        }
    }

    private fun addToGoogleCalender(event: Event) {
        val calIntent = Intent(Intent.ACTION_EDIT)
        calIntent.data = CalendarContract.Events.CONTENT_URI
        calIntent.putExtra(CalendarContract.Events.TITLE, event.name)
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, event.name)

        if (event.date != null && event.time != null) {
            val dateFormat =
                SimpleDateFormat(GenericDateFormatConstant.originEventDateTimeFormatWithTimeZone, Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.timeZone = TimeZone.getTimeZone(GenericDateFormatConstant.timeZoneGMT7)
            calendar.time = dateFormat.parse(event.date + " " + event.time)
            calIntent.putExtra(
                CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                calendar.timeInMillis
            )
            calIntent.putExtra(
                CalendarContract.EXTRA_EVENT_END_TIME,
                calendar.timeInMillis + GenericDateFormatConstant.nintyMinutesToMilliSeconds //default 90 menits
            )
        }

        context?.startActivity(calIntent)
    }
}