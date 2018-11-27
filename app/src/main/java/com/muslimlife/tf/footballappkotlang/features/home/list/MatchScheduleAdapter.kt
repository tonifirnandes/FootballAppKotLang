package com.muslimlife.tf.footballappkotlang.features.home.list

import android.content.Context
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

    }

//    private fun getGoogleCalendarId(): Long {
//
//        val projection = arrayOf(CalendarContract.Calendars._ID, CalendarContract.Calendars.NAME, CalendarContract.Calendars.ACCOUNT_NAME, CalendarContract.Calendars.ACCOUNT_TYPE)
//
//        if (CalendarHelper.haveCalendarReadWritePermissions(this)) {
//            val calCursor = this.contentResolver
//                .query(
//                    CalendarContract.Calendars.CONTENT_URI, projection,
//                    CalendarContract.Calendars.VISIBLE + " = 1", null, CalendarContract.Calendars._ID + " ASC")
//
//            if (calCursor!!.moveToFirst()) {
//                do {
//                    val id = calCursor.getLong(0)
//                    return id
//
//                } while (calCursor.moveToNext())
//            }
//        }
//        return -1
//
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//
//        if (requestCode == CalendarHelper.CALENDARHELPER_PERMISSION_REQUEST_CODE) {
//            if (CalendarHelper.haveCalendarReadWritePermissions(this)) {
//                Toast.makeText(this, "Have Calendar Read/Write Permission.",
//                    Toast.LENGTH_LONG).show()
//
//            }
//
//        }
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//
//    private fun addEventToGoogleCalendar() {
//
//        if (event.intHomeScore != null) {
//            toast("This event has passed, please choose the upcoming one!")
//        } else {
//            val calId = getGoogleCalendarId()
//            if (calId == -1L) {
//                Toast.makeText(this, "Somethings went wrong, try again!",
//                    Toast.LENGTH_SHORT).show()
//                return
//            }
//            val title = event.strEvent
//            val clock = event.strTime.split("+")[0]
//            val dt = event.dateEvent
//            val dateWithClock = "$dt $clock"
//            val simpleDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
//            val date = simpleDate.parse(dateWithClock)
//
//            val timeInMillis = date.time
//
//            //add end time to 90 minutes
//            val end = timeInMillis + 5400000
//            val intent = Intent(Intent.ACTION_EDIT)
//            intent.type = "vnd.android.cursor.item/event"
//            intent.putExtra(CalendarContract.Events.TITLE, title)
//            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, timeInMillis)
//            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)
//            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Television")
//            startActivity(intent)
//        }
//    }

}