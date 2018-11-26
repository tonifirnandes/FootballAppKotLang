package com.muslimlife.tf.footballappkotlang.features.team.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.Team
import com.muslimlife.tf.footballappkotlang.features.team.detail.TeamDetailActivity
import kotlinx.android.synthetic.main.football_team_item.view.*
import org.jetbrains.anko.startActivity

class TeamsAdapter(private val teamList: List<Team>, private val context: Context?) :
    RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.football_team_item,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = teamList.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teamList[position])
    }

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(team: Team) {
            itemView.tv_team_name.text = team.name
            Glide.with(itemView.iv_team_logo)
                .load(team.badgeUrl)
                .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(itemView.iv_team_logo)

            //team detail
            itemView.setOnClickListener {
                itemView.context.startActivity<TeamDetailActivity>(TeamDetailActivity.arg_team_bundle_key to team)
            }
        }
    }

}